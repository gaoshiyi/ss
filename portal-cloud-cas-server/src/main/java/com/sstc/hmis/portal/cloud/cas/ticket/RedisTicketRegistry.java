/**
 * 
 */
package com.sstc.hmis.portal.cloud.cas.ticket;

import java.util.Collection;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

import org.apereo.cas.ticket.ServiceTicket;
import org.apereo.cas.ticket.Ticket;
import org.apereo.cas.ticket.TicketGrantingTicket;
import org.apereo.cas.ticket.registry.AbstractTicketRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;

import com.sstc.hmis.permission.cas.CasConstants;
import com.sstc.hmis.portal.cloud.cas.authentication.pricipal.SstcPrincipal;

/**
  * <p> Title: RedisTicketRegistry </p>
  * <p> Description:  TODO </p>
  * <p> Company: SSTC </p> 
  * @author  Qxiaoxiang
  * @date  2017年12月5日 下午3:18:51
   */
public class RedisTicketRegistry extends AbstractTicketRegistry{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(RedisTicketRegistry.class);
	
	@NotNull
	private RedisTemplate<String,Ticket> client;
	@NotNull
	private StringRedisTemplate stringRedisTemplate;

	public void setStringRedisTemplate(StringRedisTemplate stringRedisTemplate) {
		this.stringRedisTemplate = stringRedisTemplate;
	}

	public void setRedisTemplate(RedisTemplate<String,Ticket> client) {
		this.client = client;
	}
	
	@PostConstruct
	public void init(){
		Assert.notNull(client, "redisTemplate不能为null");
	}

    public void deleteAll() {
        final Set<String> redisKeys = this.client.keys(CasConstants.getPatternTicketRedisKey());
        final int size = redisKeys.size();
        LOGGER.info("删除所有TGT{}条数据",size);
        this.client.delete(redisKeys);
    }
    
    @Override
    public boolean deleteSingleTicket(final String ticketId) {
        try {
            final String redisKey = CasConstants.getTicketRedisKey(ticketId);
            this.client.delete(redisKey);
            return true;
        } catch (final Exception e) {
            LOGGER.error("Ticket not found or is already removed. Failed deleting [{}]", ticketId, e);
        }
        return false;
    }

    @Override
    public void addTicket(final Ticket ticket) {
        try {
            LOGGER.debug("Adding ticket [{}]", ticket);
            final String redisKey = CasConstants.getTicketRedisKey(ticket.getId());
            final Ticket encodeTicket = this.encodeTicket(ticket);
            this.client.boundValueOps(redisKey)
                    .set(encodeTicket, getTimeout(ticket), TimeUnit.SECONDS);
            if(ticket instanceof ServiceTicket){
            	stTgtMapping(ticket, stringRedisTemplate);
            }
        } catch (final Exception e) {
            LOGGER.error("Failed to add [{}], error: {}", ticket, e);
        }
    }

    @Override
    public Ticket getTicket(final String ticketId) {
        try {
            final String redisKey = CasConstants.getTicketRedisKey(ticketId);
            final Ticket t = this.client.boundValueOps(redisKey).get();
            if (t != null) {
                final Ticket result = decodeTicket(t);
                if (result != null && result.isExpired()) {
                    LOGGER.debug("Ticket [{}] has expired and is now removed from the cache", result.getId());
                    deleteSingleTicket(ticketId);
                    return null;
                }
                return result;
            }
        } catch (final Exception e) {
            LOGGER.error("Failed fetching [{}] ", ticketId, e);
        }
        return null;
    }

    @Override
    public Collection<Ticket> getTickets() {
        return this.client.keys(CasConstants.getPatternTicketRedisKey()).stream()
                .map(redisKey -> {
                    final Ticket ticket = this.client.boundValueOps(redisKey).get();
                    if (ticket == null) {
                        this.client.delete(redisKey);
                        return null;
                    }
                    return ticket;
                })
                .filter(Objects::nonNull)
                .map(this::decodeTicket)
                .collect(Collectors.toSet());
    }

    @Override
    public void updateTicket(final Ticket ticket) {
        try {
            LOGGER.debug("Updating ticket [{}]", ticket);
            final Ticket encodeTicket = this.encodeTicket(ticket);
            final String redisKey = CasConstants.getTicketRedisKey(ticket.getId());
            this.client.boundValueOps(redisKey).set(encodeTicket, getTimeout(ticket), TimeUnit.SECONDS);
        } catch (final Exception e) {
            LOGGER.error("Failed to update [{}]", ticket);
        }
    }

	
    private static int getTimeout(final Ticket ticket) {
        final int ttl = ticket.getExpirationPolicy().getTimeToLive().intValue();
        if (ttl == 0) {
            return 1;
        }
        return ttl;
    }
	
	
	/**
	 * st和tgt的关联映射
	 * @param ticket
	 * @param client
	 */
	private static void stTgtMapping(final Ticket ticket,StringRedisTemplate stringRedisTemplate){
		TicketGrantingTicket tgt = ticket.getGrantingTicket();
		String stId = ticket.getId();
		String account = ((SstcPrincipal)tgt.getAuthentication().getPrincipal()).getId();
		String tgtId = tgt.getId();
		CasConstants.setAccountTgtId(account, tgtId, stringRedisTemplate);
		CasConstants.setStTgtMapping(stId, tgtId, stringRedisTemplate);
	}
	
	/**
	 * 根据st获取tgt
	 * @param ticket ServiceTicket
	 * @param client redisTemplate
	 * @return tgt
	 */
	public static Ticket getStTgtMapping(final Ticket ticket,RedisTemplate<String, Ticket> client){
		String stId = ticket.getId();
		String key = CasConstants.getStTgtKey(stId);
		return client.opsForValue().get(key);
	}
	

}

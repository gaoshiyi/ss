/**
 * stop 订阅消息客户端
 * 
 * @author wangzhuhua
 * @date 2018-04-24
 */
(function(){
	
	var StomClient = function(endPoint, topic, onConnect, onError, onMessage){
		this._client = null;
		// webservice endpoint
		this.endPoint = endPoint;
		// 订阅的主题
		this.topic = topic;
		
		this.onConnect = onConnect;
		this.onError = onError;
		this.onMessage = onMessage;
	};
	
	StomClient.prototype.connect = function(){
		
		var _this = this;

		console.log(_this.endPoint);
		// 建立stomp客户端
		var socket = new SockJS(_this.endPoint);
	    var stompClient = Stomp.over(socket);
	    stompClient.connect({}, function (frame) {
	    	_this.onConnect();
	        stompClient.subscribe(_this.topic, function (response) {
	        	_this.onMessage(JSON.parse(response.body));
	        })
	    },function errorCallBack (error) {
	    	console.log(error);
	    	_this.onError(error);
	        
	    	// 三秒重试一次链接
	        window.setTimeout(function(){
	        	_this.disconnect();
	        	_this.connect();
	        }, 3000);
	    });
	    
	    _this._client = stompClient;
	    
	    return stompClient;
	};
	
	
	StomClient.prototype.disconnect = function (callback) {
	    if (this._client != null) {
	        this._client.disconnect();
	    }
	    
	    if(typeof callback != 'undefined'){
		    callback();
	    }
	};
	
	window.StomClient = StomClient;
}).call(this);
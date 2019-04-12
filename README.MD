*本版本主要工作：解析request请求

浏览器在连接上服务端后会发送一个标准的HTTP请求,而每个请求由三部分构成(请求行,消息头,消息正文).因此我们设计一个类HttpRequest,用这个类的每个实例表示一个浏览器发送过来的请求内容,以便后续处理请求时使用.


实现
1:在com.wsbserver下新建一个包:http，在这个包中定义所有有关HTTP协议的类.

2:在com.webserver.http包中定义类:HttpRequest.

3:在HttpRequest中定义请求各部分内容对应的属性.

4:实现解析操作.

5:在ClientHandler中实例化一个HttpRequest，完成对请求的解析工作.




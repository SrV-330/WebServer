*本版本主要工作:解析request请求

浏览器在连接上服务端后会发送一个标准的HTTP请求,而每个请求由三部分构成(请求行,消息头,消息正文).因此我们设计一个类HttpRequest,用这个类的每个实例表示一个浏览器发送过来的请求内容,以便后续处理请求时使用.


实现
1:在com.wsbserver下新建一个包:http，在这个包中定义所有有关HTTP协议的类.

2:在com.webserver.http包中定义类:HttpRequest.

3:在HttpRequest中定义请求各部分内容对应的属性.

4:实现解析操作.

5:在ClientHandler中实例化一个HttpRequest，完成对请求的解析工作.


*本版本主要工作:创建一个网络应用中所需要的测试页面


WebServer是一个网络容器.管理着多个网络应用(WebApp)
每一个网络应用都应该包含(页面,静态资源,业务代码等).

因此我们在WebServer项目目录下新建一个目录webapps,
用这个目录保存所有的网络应用,每个网络应用都保存在webapps下的一个单独目录中,而该目录的名字就是在这个网络应用的名字.


实现:
1:在WebServer项目目录下新建一个目录:webapps

2:在webapps目录下新建一个子目录:myweb

3:在myweb目录下新建第一个页面:index.html

4:完成ClientHandler中处理客户端求情的第二部工作:
	处理请求
	由于我们将每个网络应用都放在了webapps目录下了,因此浏览器输入url地址时的资源路径所对应的目录应当就是从webapps下开始的.
	例如:
	http://localhost:8080/myweb/index.html
	那么我们在解析请求行中抽象目录得到的部分就是:
	/myweb/index.html
	这时我们从webapps目录下根据该抽象路径应当就可以找到该文件.
	
*本版本主要工作:实现响应客户端的工作

v3版本中ClientHandler在处理客户端的请求环节会根据客户端请求的资源路径从webapps中对应去寻找该资源,
并添加了分支判断(资源是否存在)
若存在则应当将该资源相应给客户端,若不存在则应当相应404页面.
本版本主要完成响应工作,与请求一样,HHTP协议也对服务端给客户端发送的内容作了规定,这就是HTTP响应(Response)
哦们必须按照该协议规定的格式向客户端发送内容.

当ClientHandler根据请求中的资源找不到对应的资源时应当给客户端响应404页面.

404页面应当是一个独立于网络应用的(因为不同的网络应用都会存在请求资源不存在的情况,这时都影响应该页面)

实现:
1:在webapps目录下新建一个目录root
2:在root目录下新建一个页面:404.html
	在该页面中巨宗显示一句话:404!该资源不存在!
3:在ClintHandler中当资源不存在时响应404页面给客户端
	需要注意:响应中的状态行里代码应当为404,状态描述应当为NOT FOUND
	响应头和正文参照正常响应资源.


*本版本主要工作:代码重构
与请求相同,我们可以再设计一个类HttpResponse并使用这类的每个实例表示服务端给客户发送的一个响应.
将响应内容发送给客户端的工作也定义在HttpResponse
使代码重用.


实现:
1:在com.webserver.http包中定义类HttpRsponse

2:在HttpResponse中定义一个响应所对应的三部分的属性

3:为属性提供get,set方法

4:定义一个flush
这个方法用来将当前响应对象的内容以一个标准的HTTP响应格式发送给客户端(原ClientHandler的工作)

5:ClientHandler在发送响应时改为调用HttpResponse的flush方法.

*本版本主要工作:解决空请求问题
HTTP协议允许客户端发送空请求,并且浏览器发送空请求后就会与服务端断开连接.但是以我们现在的实现来看,若发送的是空请求,解析请求中的请求行为时会出现下标越界的情况(因为我们会将请求行拆分成3项解析),并且向客户端发送响应,由于客户端断开,会出现"断开的管道"
这样的异常.
对此,在解析空请求时停止本次处理后续的一切操作.

实现思路:在HttpRequest解析发现空请求,抛出异常给ClientHandler,ClientHandler捕获后跳过后续步骤.

实现:

1:在com.webserver.core包中创建自定义异常EmptyRequestException:空请求异常

2:在HttpRequest的解析请求方法:中判定,为空则抛出异常

3:HttpRequest捕获向上抛出ClientHandler

4:ClientHandler捕获后跳过请求直接与客户端断开.


*本版本主要工作:重构HttpResponse发送响应头的工作

之前版本中HttpResponse发送响应头的方法是固定的,需根据请求改变资源类型

1:发送响应的头根据实际设置.

2:设置Content-Type以便浏览器正确解析

3:在com.webserver.http包中定义一个类:HttpContext这个类中含有所有HTTP协议内容的定义,
	先定义一个Map mimeMapping用于存放Content-Type中的值.
	
4:ClientHandler中可根据资源后缀获取Content-Type.

*本版本主要工作:接受用户输入数据
注册,登录

1:在webapps/myweb目录下新建注册页面:reg.html
2:重构HttpRequest 解析用户输入参数

在HttpRequest中定义3个属性:String requestURI,String queryString,Map paramters保存参数.

*本版本主要工作:实现注册

当请求是业务,处理业务;
当请求为静态资源,发送资源.


*本版本主要工作:实现登录

实现:
1:创建页面login.html,用form提交用户数据,路径为"./login"
	成功页面 login_success.html
	失败页面 login_fail.html 一律提示:用户名或密码不正确/

2:在ClientHandler判断请求.

3:创建LoginServlet,在service中处理登录

*本版本主要工作:重构代码

根据不同的业务定义相应的Servlet来处理,定义一个超类,在超类中定义所有Servlet都会使用的方法.

实现:

1:在com.webserver.servlet中定义HttpServlet
	该类是抽象类.
2:在超类中定义方法抽象service.

3:所有Servlet继承HttpServlet.


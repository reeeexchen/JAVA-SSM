<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<%--
<%@include...%>   静态包含：会将引用的源代码原封不动的附加过来，
            合并过来成一个jsp，对应一个servlet。
<jsp:include...>  动态包含：分别编译，被包含的jsp独立编译成servlet，
            然后和包涵的jsp页面编译生成的静态文档html做合并；
            总是会检查所包含文件的变化,适合包含动态文件。
 静态包含是被包含的JSP合并到该servlet中。(一个servlet)
 动态包含是被包含的JSP先运行servlet,再把运行结果合并到包含的html中(多个servlet)。
--%>

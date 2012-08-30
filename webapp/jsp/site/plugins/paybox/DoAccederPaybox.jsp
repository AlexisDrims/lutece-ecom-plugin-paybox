<%@ page errorPage="../../ErrorPagePortal.jsp" 
	import="fr.paris.lutece.plugins.paybox.web.PayboxJspBean,fr.paris.lutece.portal.service.spring.SpringContextService"%>
<%
	PayboxJspBean payboxJspBean = ( (PayboxJspBean) SpringContextService
            .getBean( "paybox.payboxJspBean" ) );
	payboxJspBean.getPayboxAccess( request, response );
%>
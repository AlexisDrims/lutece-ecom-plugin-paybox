<%@ page errorPage="../../ErrorPage.jsp" %>
<jsp:include page="../../AdminHeader.jsp" />

<jsp:useBean id="payboxAdmin" scope="session" class="fr.paris.lutece.plugins.paybox.web.PayboxAdminJspBean" />

<% payboxAdmin.init( request, payboxAdmin.RIGHT_READ ); %>
<%= payboxAdmin.getLogs( request ) %>

<%@ include file="../../AdminFooter.jsp" %>
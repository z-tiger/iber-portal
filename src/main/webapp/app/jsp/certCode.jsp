<%@page contentType="image/jpeg" %>
<jsp:useBean id="image" scope="page" class="com.iber.portal.common.makeCertPic" />
<%

String str = image.getCertPic(0,0,response.getOutputStream());
session.setAttribute("InvitationCertCode", str);
out.clear();
out = pageContext.pushBody();

%>
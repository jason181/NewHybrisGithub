<%--
  Created by IntelliJ IDEA.
  User: chris
  Date: 19/01/2021
  Time: 9:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="template" tagdir="/WEB-INF/tags/responsive/template" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="cms" uri="http://hybris.com/tld/cmstags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="product" tagdir="/WEB-INF/tags/responsive/product" %>

<spring:htmlEscape defaultHtmlEscape="true"/>
<template:page pageTitle="${pageTitle}">
    <div class="container">
        <div class="row">
            <div class="training">
                <div class="middle-content">
                    <cms:pageSlot position="MiddleContent" var="comp" element="div" class="errorNotFoundPageMiddle">
                        <cms:component component="${comp}" element="div" class="errorNotFoundPageMiddle-component"/>
                    </cms:pageSlot>
                </div>
                <div class="bottom-content">
                    <cms:pageSlot position="BottomContent" var="comp" element="div" class="errorNotFoundPageBottom">
                        <cms:component component="${comp}" element="div" class="errorNotFoundPageBottom-component"/>
                    </cms:pageSlot>
                </div>
            </div>
            <c:forEach items="${product}" var="product">
                <div class="training-product-grid col-md-3">
                    <c:url value="${product.url}" var="productUrl" />
                    <a href="${fn:escapeXml(productUrl)}" class="thumb" title="${fn:escapeXml(product.name)}">
                        <product:productPrimaryImage product="${product}" format="product"/>
                    </a>
                    <div class="training-label">
                        <p>${product.code}</p>
                        <p>${product.name}</p>
                        <p>${product.material}</p>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</template:page>
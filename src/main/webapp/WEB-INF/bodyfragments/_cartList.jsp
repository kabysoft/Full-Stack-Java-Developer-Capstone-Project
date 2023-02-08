<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="ISO-8859-1"%>

<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="crt"%>
<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@page isELIgnored="false"%>

<c:url var="addUrl" value="/ctl/cart" />

<c:url var="addSearch" value="/ctl/cart/search" />

<c:url var="editUrl" value="/ctl/cart?id=" />


<br>
<div class="container"> 
<nav aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item linkSize"><i class="fas fa-tachometer-alt"></i> <a class="link-dark" href="<c:url value="/welcome"/>">Home</a></li>
    <li class="breadcrumb-item linkSize active" aria-current="page"> <i class="fa fa-arrow-right" aria-hidden="true"></i> Cart List</li>
  </ol>
</nav>
</div>
<hr>
	<sf:form method="post"
		action="${pageContext.request.contextPath}/ctl/cart/search"
		modelAttribute="form">
		<div class="card">
			<h5 class="card-header"
				style="background-color: #00061df7; color: white;">Cart List</h5>
			<div class="card-body">
				<div class="row g-3">
				
				</div>
				<b><%@ include file="businessMessage.jsp"%></b>
				<br>
				<sf:input type="hidden" path="pageNo" />
				<sf:input type="hidden" path="pageSize" />

				<sf:input type="hidden" path="listsize" />
				<sf:input type="hidden" path="total" />
				<sf:input type="hidden" path="pagenosize" />

				<table class="table table-bordered border-primary">
					<thead>
						<tr>
						
							<th scope="col">#</th>
							<th scope="col">Name</th>
							<th scope="col">CompanyName</th>
							<th scope="col">Price</th>
							<th scope="col">Quantity</th>
							<th scope="col">Total Price</th>
							<th scope="col">Update</th>
							<th scope="col">Delete</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${list}" var="pl" varStatus="cart">
							<tr>
						
								<td scope="row">${cart.index+1}</td>
								<td scope="row">${pl.medicine.name}</td>
								<td scope="row">${pl.medicine.companyName}</td>
								<td scope="row">${pl.price}</td>
								<td scope="row"><input type="text"
									name="qunatity${cart.index+1}" class="form-control"
									value="${pl.quantity}"></td>
								<td scope="row">${pl.finalPrice}</td>
								<c:if test="${sessionScope.user.roleName == 'user'}">
								<td><input type="submit" value="Update" name="operation"
									class="btn btn-primary btn btn-info"></td>
								<td><a href="<c:url value="/ctl/cart/search?cid=${pl.id}&operation=Delete"/>"
									class="btn btn-primary btn btn-info">Remove</a></td>
								</c:if>
								
							</tr>
							
							<c:set var="totalp" value="${totalp + pl.finalPrice}" />
						</c:forEach>
						<tr>
							<td colspan="5">Total</td>
							<td colspan="3">${totalp}</td>
						</tr>
					</tbody>
				</table>
				
				<div class="clearfix">
				
				<c:if test="${listsize>0}">
						<input type="submit" value="Checkout" name="operation"
							class="btn  btn-info">
				</c:if>
				
			<nav aria-label="Page navigation example float-end">
				<ul class="pagination justify-content-end" style="font-size: 13px">
					<li class="page-item"><input type="submit" name="operation"
								class="page-link" 	<c:if test="${form.pageNo == 1}">disabled="disabled"</c:if>
								value="Previous"></li>
								 <c:forEach var = "i" begin = "1" end = "${(listsize/10)+1}">
								 <c:if test="${i== pageNo}">
								<li class="page-item active"><a class="page-link activate" href="${addSearch}?pageNo=${i}">${i}</a></li>
								</c:if>
								<c:if test="${i != pageNo}">
								<li class="page-item"><a class="page-link" href="${addSearch}?pageNo=${i}">${i}</a></li>
								</c:if>
								</c:forEach>
					<li class="page-item"><input type="submit" name="operation"
								class="page-link" <c:if test="${total == pagenosize  || listsize < pageSize   }">disabled="disabled"</c:if>
								value="Next"></li>
				</ul>
			</nav>
			</div>
				
				
			</div>

		</div>
	</sf:form>

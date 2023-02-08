<%@taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="crt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
<br>
<div class="container"> 
<nav aria-label="breadcrumb">
  <ol class="breadcrumb">
    <li class="breadcrumb-item linkSize"><i class="fas fa-tachometer-alt"></i> <a class="link-dark" href="<c:url value="/welcome"/>">Home</a></li>
    <li class="breadcrumb-item linkSize active" aria-current="page"> <i class="fa fa-arrow-right" aria-hidden="true"></i>Check Out</li>
  </ol>
</nav>
</div>
<hr>
<div class="container">


	<sf:form method="post"
		action="${pageContext.request.contextPath}/ctl/order"
		modelAttribute="Dto">

		<div class="card">
			<h5 class="card-header"
				style="background-color: #00061df7; color: white;">Check Out</h5>
			<div class="card-body">
				<div class="row g-3">
					<div class="col-md-5 col-lg-4 order-md-last">
						<h4 class="d-flex justify-content-between align-items-center mb-3">
							<span class="text-muted">Item Detail</span>
						</h4>

						<table class="table table-bordered border-primary">
							<thead>
								<tr>

									<th scope="col">Name</th>
									<th scope="col">Price</th>
									<th scope="col">Quantity</th>
									<th scope="col">Total</th>
								</tr>
							</thead>
							<tbody>
								<c:set var="totalp" value="${0}" />
								<c:forEach items="${cList}" var="ct" varStatus="cart">
									<tr>
										
										<td scope="row">${ct.medicine.name}</td>
										<td scope="row">${ct.medicine.price}</td>
										<td scope="row">${ct.quantity}</td>
										<td scope="row">${ct.finalPrice}</td>
									</tr>
									<c:set var="totalp" value="${totalp + ct.finalPrice}" />
								</c:forEach>
								<tr>
									<td colspan="3">Total</td>
									<td colspan="3">${totalp}</td>
								</tr>

							</tbody>
						</table>

					</div>

					<div class="col-md-7 col-lg-8">
						<h4 class="mb-3">Billing address</h4>
						<div class="row g-3">
							<div class="col-sm-6">
								<s:bind path="name">
									<label for="firstName" class="form-label">Name</label>
									<sf:input  class="form-control" 
										path="${status.expression}" placeholder="Enter Name" required="required" />
								</s:bind>
							</div>

							<div class="col-sm-6">
							<s:bind path="phoneNo">
									<label for="firstName" class="form-label">Mobile No</label>
									<sf:input  class="form-control" id="phoneNo"
										path="${status.expression}" placeholder="Enter Phone No"  required="required" />
								</s:bind>

							</div>


							<div class="col-12">
								<s:bind path="email">
									<label for="firstName" class="form-label">Email</label>
									<sf:input  class="form-control" id="firstName"
										path="${status.expression}" placeholder="Enter Email"  required="required" />
								</s:bind>

							</div>

							<div class="col-md-5">
								<s:bind path="state">
									<label for="firstName" class="form-label">State</label>
									<sf:input  class="form-control" id="firstName"
										path="${status.expression}" placeholder="Enter State"  required="required" />
								</s:bind>
							</div>

							<div class="col-md-4">
								<s:bind path="city">
									<label for="firstName" class="form-label">City</label>
									<sf:input  class="form-control" id="firstName"
										path="${status.expression}" placeholder="Enter City"  required="required" />
								</s:bind>
							</div>


							<div class="col-12">
							<s:bind path="address1">
								<label for="address" class="form-label">Address</label>
								<sf:textarea  class="form-control" id="firstName"
										path="${status.expression}" placeholder="Enter address line 1 " rows="4" cols="4" required="required" />
							</s:bind>
							</div>

							<div class="col-12">
									<s:bind path="address2">
								<label for="address" class="form-label">Address 2</label>
								<sf:textarea  class="form-control" id="firstName"
										path="${status.expression}" placeholder="Enter address line 2 " rows="4" cols="4" required="required" />
							</s:bind>
							</div>


						</div>

						<hr class="my-4">

						<input type="submit" name="operation" value="Payment"
							class="btn btn-primary btn btn-info" />


					</div>


				</div>

			</div>

		</div>
	</sf:form>
</div>


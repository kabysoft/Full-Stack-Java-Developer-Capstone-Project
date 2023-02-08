<%@page import="com.ehealth.cms.constant.EMSView"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jstl/core_rt" prefix="crt"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="s"%>
	<nav class="navbar navbar-expand-lg"
		style="height: 59px; background-color: #00061df7;">
		<div class="container-fluid">
			<a class="navbar-brand " href="#"
				style="font-size: 26px; font-family: serif; color:white;">
				<span style="font-family: emoji;font-variant: petite-caps; color: #e76a00">E-Health (Medicare)</span> 
				<span>Care</span> 
				<span style="font-family: emoji;font-variant: petite-caps; color: #e76a00">Medicine Shop</span></a>


			<div class="collapse navbar-collapse" id="navbarText">
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
				</ul>
				<c:if test="${sessionScope.user != null}">
				<span class="navbar-text" style="color: white;font-size: 14px; font-variant-caps:petite-caps;"> Hii,${sessionScope.user.name}
				</span>
				</c:if>
				<c:if test="${sessionScope.user == null}">
				<span class="navbar-text" style="color: white;font-size: 14px; font-variant-caps:petite-caps;"> Hii,Guest
				</span>
				</c:if>
			</div>
		</div>
	</nav>
	<div class="shadow bg-body rounded">
		<nav class="navbar navbar-expand-lg "
			style="height: 39px; background-color: #01081dd9;">
			<div class="container-fluid">
				<button class="navbar-toggler" type="button"
					data-bs-toggle="collapse" data-bs-target="#navbarNav"
					aria-controls="navbarNav" aria-expanded="false"
					aria-label="Toggle navigation">
					<span class="navbar-toggler-icon"></span>
				</button>
				<div class="collapse navbar-collapse" id="navbarNav">
					<ul class="navbar-nav">
						<li class="nav-item linkSize"><a class="nav-link active link-light"
							aria-current="page" href="<c:url value="/welcome"/>">Home</a></li>
						<c:if test="${sessionScope.user != null}">
							<c:if test="${sessionScope.user.roleName == 'admin'}">
						<li class="nav-item linkSize"><a class="nav-link link-light" href="<c:url value="<%=EMSView.USER_LIST_CTL%>"/>">User List</a></li>
						<li class="nav-item linkSize"><a class="nav-link link-light" href="<c:url value="<%=EMSView.MEDICINE_CTL%>"/>">Add Medicine</a></li>
						<li class="nav-item linkSize"><a class="nav-link link-light" href="<c:url value="<%=EMSView.MEDICINE_LIST_CTL%>"/>">Medicine List</a></li>
						<li class="nav-item linkSize"><a class="nav-link link-light"
								href="<c:url value="/ctl/order/search"/>">Order List</a></li>
								<li class="nav-item linkSize"><a class="nav-link link-light"
								href="<c:url value="/ctl/order/search/month"/>">Order Month List</a></li>
								<li class="nav-item linkSize"><a class="nav-link link-light"
								href="<c:url value="/ctl/order/search/year"/>">Order Year List</a></li>
				
						</c:if>
						
						<c:if test="${sessionScope.user.roleName == 'user'}">
						<li class="nav-item linkSize"><a class="nav-link link-light" href="<c:url value="<%=EMSView.MEDICINE_LIST_CTL%>"/>">Medicine List</a></li>
						<li class="nav-item linkSize"><a class="nav-link link-light" href="<c:url value="<%=EMSView.CART_LIST_CTL%>"/>">Cart</a></li>
						<li class="nav-item linkSize"><a class="nav-link link-light"
								href="<c:url value="/ctl/order/search"/>">Order List</a></li>
						</c:if>
						
						</c:if>
						<c:if test="${sessionScope.user == null}">
						<li class="nav-item linkSize"><a class="nav-link link-light" href="<c:url value="/aboutUs"/>">AboutUs</a></li>
						<li class="nav-item linkSize"><a class="nav-link link-light" href="<c:url value="/contactUs"/>">ContactUs</a></li>
						</c:if>
					</ul>
				</div>
				<ul class="nav justify-content-end">
				<c:if test="${sessionScope.user != null}">
				
				
				
				<li class="nav-item linkSize"><a class="nav-link link-light" style="padding: 6px;"
						href="<c:url value="/profile"/>">My Profile</a></li>
				<li class="nav-item linkSize"><a class="nav-link link-light" style="padding: 6px;"
						href="<c:url value="/changepassword"/>">Change Password</a></li>
				
					<li class="nav-item linkSize"><a class="nav-link link-light" style="padding: 6px;"
						href="<c:url value="/login"/>">Logout</a></li>
				</c:if>
				<c:if test="${sessionScope.user == null}">
					<li class="nav-item linkSize"><a class="nav-link link-light" style="padding: 6px;"
						href="<c:url value="/login"/>">SignIn</a></li>
					<li class="nav-item linkSize"><a class="nav-link link-light" style="padding: 6px;"
						href="<c:url value="/signUp"/>">SignUp</a></li>
				</c:if>
					
				</ul>
			</div>
		</nav>
	</div>

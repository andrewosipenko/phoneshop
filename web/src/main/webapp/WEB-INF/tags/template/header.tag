<%@tag pageEncoding="utf-8" trimDirectiveWhitespaces="true" %>
<%@ attribute name="cartQuantity"%>
<%@ attribute name="cartSubTotal"%>

<nav class="navbar navbar-dark bg-dark pr-5 pl-5 pd-5">
  <div>
    <a class="navbar-brand" href="#"><h2>Phoneshop</h2></a>
  </div>
  <div>
    <div class="float-right" >
      <a href="#">Login</a>
    </div>
    <div>
      <button class="btn btn-warning">My cart: <span id="cart-amount">${cartQuantity}</span> items <span id="cart-price">${cartSubTotal}</span>$</button>
    </div>
  </div>
</nav>

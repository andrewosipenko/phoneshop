<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<p>
  Product details for <b> ${phone.brand} ${phone.model} : </b>
</p>

<p>
 <img src="https://raw.githubusercontent.com/andrewosipenko/phoneshop-ext-images/master/${phone.imageUrl}">
<p>

<p>
<b>  Price: $ ${phone.price} </b>
</p>

<p>
<b> Colors available:
<c:forEach var="color" items="${colors}">
<c:out value = "${color.code}  " />
</c:forEach>
</b>
</p>

<p>
<b> Information: </b>
</p>
<p>
  Device type: ${phone.deviceType}
</p>
<p>
  OS: ${phone.os}
</p>
<p>
  Display size in inches: ${phone.displaySizeInches}
</p>
<p>
  Display resolution: ${phone.displayResolution}
</p>
<p>
  Pixel density: ${phone.pixelDensity}
</p>
<p>
  Display technology: ${phone.displayTechnology}
</p>
<p>
  Back camera (Mpx): ${phone.backCameraMegapixels}
</p>
<p>
  Front camera (Mpx): ${phone.frontCameraMegapixels}
</p>
<p>
  RAM (Gb): ${phone.ramGb}
</p>
<p>
  Internal storage (Gb): ${phone.internalStorageGb}
</p>
<p>
  Battery capacity (Mah): ${phone.batteryCapacityMah}
</p>
<p>
  Talk time (hours): ${phone.talkTimeHours}
</p>
<p>
  Stand by time (hours): ${phone.standByTimeHours}
</p>
<p>
  Bluetooth: ${phone.bluetooth}
</p>
<p>
  Positioning: ${phone.positioning}
</p>
<p>
  Weight (gramms): ${phone.weightGr}
</p>
<p>
  Length (mm): ${phone.lengthMm}
</p>
<p>
  Width (mm): ${phone.widthMm}
</p>
<p>
  Height (mm): ${phone.heightMm}
</p>
<p>
  Date announced: ${phone.announced}
</p>

<p>
<b> Description: </b>
${phone.description}
</p>


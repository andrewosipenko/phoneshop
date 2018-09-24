function submitForm(form) {
      var quantity = form.elements["quantity"].value;
      var phoneId = form.elements["phoneId"].value;
      $.ajax({
          type: 'POST',
          data: {
              quantity: quantity,
              phoneId: phoneId
          },
          url: ctx + "ajaxCart",
          success: (function(data) {
              $("#resultItems").text(data.quantities);
              $("#resultPrice").text(data.overallPrice);
              $("#error" + phoneId).text(data.errorMsg);
          }),
          error: (function(jqXHR) {
              alert(jqXHR.statusText);
          })
      });
  };

    $(function () {
      var token = $("meta[name='_csrf']").attr("content");
      var header = $("meta[name='_csrf_header']").attr("content");
      $(document).ajaxSend(function(e, xhr, options) {
        xhr.setRequestHeader(header, token);
      });
    });

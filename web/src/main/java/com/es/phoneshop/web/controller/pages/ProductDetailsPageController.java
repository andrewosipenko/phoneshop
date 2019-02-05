package com.es.phoneshop.web.controller.pages;

import com.es.core.model.comment.Comment;
import com.es.core.model.phone.Phone;
import com.es.core.service.comment.CommentService;
import com.es.core.service.phone.PhoneService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/productDetails")
public class ProductDetailsPageController {
    private final String ATTRIBUTE_LOGIN = "login";
    private final String ATTRIBUTE_PHONE = "phone";
    private final String ATTRIBUTE_COMMENT_FORM = "commentForm";
    private final String ATTRIBUTE_COMMENTS = "comments";
    private final String ATTRIBUTE_SUCCESS_MESSAGE = "message";
    private final String MESSAGE_SUCCESS = "Your comment was added!";
    private final String PAGE_PRODUCT_DETAILS = "productDetails";

    @Resource(name = "phoneServiceImpl")
    private PhoneService phoneService;
    @Resource
    private CommentService commentService;

    @RequestMapping(method = RequestMethod.GET, value = "/phoneId={phoneId}")
    public String showProductDetails(@PathVariable Long phoneId, Model model, Authentication authentication){
        if (authentication != null && authentication.isAuthenticated()){
            model.addAttribute(ATTRIBUTE_LOGIN, authentication.getName());
        }
        Phone phone = phoneService.getPhone(phoneId).get();
        model.addAttribute(ATTRIBUTE_PHONE, phone);
        List<Comment> comments = commentService.findAll(phoneId);
        model.addAttribute(ATTRIBUTE_COMMENTS, comments);
        Comment commentForm = new Comment();
        model.addAttribute(ATTRIBUTE_COMMENT_FORM, commentForm);
        return PAGE_PRODUCT_DETAILS;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/phoneId={phoneId}")
    public String placeComment(@PathVariable Long phoneId, @Valid @ModelAttribute(value = "commentForm") Comment comment, BindingResult bindingResult, Model model){
        Phone phone = phoneService.getPhone(phoneId).get();
        model.addAttribute(ATTRIBUTE_PHONE, phone);
        if(bindingResult.hasErrors()){
            return PAGE_PRODUCT_DETAILS;
        }
        comment.setPhoneId(phoneId);
        commentService.saveComment(comment);
        model.addAttribute(ATTRIBUTE_SUCCESS_MESSAGE, MESSAGE_SUCCESS);
        return PAGE_PRODUCT_DETAILS;
    }
}

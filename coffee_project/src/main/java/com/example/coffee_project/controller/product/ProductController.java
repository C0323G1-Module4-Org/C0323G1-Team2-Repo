package com.example.coffee_project.controller.product;

import com.example.coffee_project.dto.product.ProductDto;
import com.example.coffee_project.model.product.Product;
import com.example.coffee_project.service.product.IProductService;
import com.example.coffee_project.service.product.IProductTypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService productService;
    @Autowired
    private IProductTypeService productTypeService;

    @GetMapping("/list")
    public String display(@PageableDefault(size = 8, sort = "productPrice") Pageable pageable, Model model) {
        model.addAttribute("productTypes", productTypeService.display());
        String[] prices = {"10-50", "50-100", "100-150", "150-200"};
        model.addAttribute("prices", prices);
        Page<Product> products = productService.display(pageable);
        model.addAttribute("products", products);
        return "product/list";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = "product_price") Pageable pageable, Model model, @RequestParam(value = "name", defaultValue = "") String name,
                         @RequestParam(defaultValue = "") String productType, @RequestParam(defaultValue = "") String price) {
        String[] prices = {"10000-50000", "50000-100000", "100000-150000", "150000-200000"};
        model.addAttribute("prices", prices);
        model.addAttribute("productTypes", productTypeService.display());
        Page<Product> products = productService.search(pageable, name, productType, price);
        model.addAttribute("products", products);
        return "product/list";
    }

    @GetMapping("")
    public String display2(@PageableDefault(size = 4, sort = "productPrice") Pageable pageable, @RequestParam
            (value = "msg", required = false) String msg, Model model) {
        model.addAttribute("msg", msg);
        Page<Product> products = productService.display(pageable);
        model.addAttribute("products", products);
        return "product/list2";
    }

    @PostMapping("/delete")
    public String delete(@RequestParam int id, RedirectAttributes redirectAttributes) {
        if (productService.findProductById(id) == null) {
            redirectAttributes.addFlashAttribute("msg", "Không tìm thấy id. ");
            return "product/list";
        }
        productService.delete(id);
        redirectAttributes.addFlashAttribute("msg", "Xóa thành công ");
        return "redirect:/product";
    }

    @GetMapping("edit/{id}")
    public String showFormEdit(@PathVariable int id, RedirectAttributes redirectAttributes, Model model) {
        if (productService.findProductById(id) == null) {
            redirectAttributes.addFlashAttribute("msg", "Không tìm thấy id. ");
            return "product/list";
        }
        model.addAttribute("productTypes", productTypeService.display());
        model.addAttribute("product", productService.findProductById(id));
        return "product/edit";
    }

    @GetMapping("create")
    public String showFormCreate(Model model) {
        model.addAttribute("productTypes", productTypeService.display());
        model.addAttribute("product", new ProductDto());
        return "product/create";
    }

    @PostMapping("/edit")
    public String edit(@Valid @ModelAttribute("product") ProductDto productDto, BindingResult bindingResult,
                       RedirectAttributes redirectAttributes, Model model) {
        if (productDto == null) {
            redirectAttributes.addFlashAttribute("msg", "Đối tượng không tồn tại. ");
            return "product/edit";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("productTypes", productTypeService.display());
            return "product/edit";
        }
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        productService.edit(product);
        redirectAttributes.addAttribute("msg", "Sửa thành công.");
        return "redirect:/product";
    }

    @PostMapping("/create")
    public String create(@Valid @ModelAttribute("product") ProductDto productDto, BindingResult bindingResult,
                         RedirectAttributes redirectAttributes, Model model) {
        if (productDto == null) {
            redirectAttributes.addFlashAttribute("msg", "Đối tượng không tồn tại. ");
            return "product/create";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("productTypes", productTypeService.display());
            return "product/create";
        }
        Product product = new Product();
        BeanUtils.copyProperties(productDto, product);
        productService.add(product);
        redirectAttributes.addAttribute("msg", "Thêm mới thành công.");
        return "redirect:/product";
    }
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable int id, Model model) {
        Product product=productService.findProductById(id);
        if (product == null) {
            model.addAttribute("msg", "Id không tồn tại.  ");
            return "product/list2";
        } else {
            model.addAttribute("product", product);
            return "product/detail";
        }
    }
}

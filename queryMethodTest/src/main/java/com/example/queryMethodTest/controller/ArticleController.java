package com.example.queryMethodTest.controller;


import com.example.queryMethodTest.Dto.ArticleDto;
import com.example.queryMethodTest.entity.Article;
import com.example.queryMethodTest.service.ArticleService;
import com.example.queryMethodTest.service.PaginationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    @Autowired
    PaginationService paginationService;
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("paging")
    public String mainView(Model model,
                           @PageableDefault(page = 0, size = 10, sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {
        // 넘겨온 페이지 번호로 리스트 받아오기
        Page<Article> paging = articleService.pagingList(pageable);
        // 페이지 블럭 처리(1,2,3,4,5)
        int totalPage = paging.getTotalPages();
        List<Integer> barNumbers=paginationService.getPaginationBarNumbers(pageable.getPageNumber(),totalPage);
        model.addAttribute("paginationBarNumbers", barNumbers);
        model.addAttribute("paging", paging);
        return "articles/show_all_list";
    }
    @GetMapping("insert")
    public String insertView(Model model) {
        model.addAttribute("articleDto", new ArticleDto());
        return "/articles/new";
    }

    @PostMapping("insert")
    public String insertArticle(@ModelAttribute("articleDto") ArticleDto dto) {
        articleService.insertArticle(dto);
        return "redirect:/";
    }

    @GetMapping("update/{id}")
    public String updateView(@PathVariable("id")Long id,
                             Model model) {
        ArticleDto articleDto = articleService.findById(id);
        model.addAttribute("articleDto", articleDto);
        return "/articles/update";
    }

    @PostMapping("update")
    public String update(@ModelAttribute("articleDto") ArticleDto dto) {
        articleService.updateArticle(dto);
        return "redirect:/";
    }

    @GetMapping("delete/{id}")
    public String deleteView(@PathVariable("id")Long id,
                             RedirectAttributes redirectAttributes) {
        //1. 삭제할 대상이 존재하는지 확인
        ArticleDto dto = articleService.findById(id);
        //2. 대상 엔티티가 존재하면 삭제처리 후 메시지를 전송
        if (dto != null) {
            articleService.deleteArticle(id);
            redirectAttributes.addFlashAttribute("msg", "정상적으로 삭제되었습니다.");
        }
        articleService.deleteArticle(id);
        return "redirect:/";
    }

}

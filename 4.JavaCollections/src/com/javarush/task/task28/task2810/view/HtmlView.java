package com.javarush.task.task28.task2810.view;

import com.javarush.task.task28.task2810.Controller;
import com.javarush.task.task28.task2810.vo.Vacancy;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HtmlView implements View {
    private Controller controller;
    private final String filePath = "./4.JavaCollections/src/" + this.getClass().getPackage().getName().replace(".", "/") + "/vacancies.html";

    @Override
    public void update(List<Vacancy> vacancies) {
        String updatedFileContent = getUpdatedFileContent(vacancies);
        updateFile(updatedFileContent);
    }

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void userCitySelectEmulationMethod() {
        controller.onCitySelect("Odessa");
    }

    protected Document getDocument() throws IOException {
        return Jsoup.parse(new File(filePath), "UTF-8");
    }

    private String getUpdatedFileContent(List<Vacancy> vacancies) {
        Document document = null;
        try {
            document = getDocument();
            Element template = document.getElementsByClass("template").first();
            Element copy = template.clone();
            copy.removeAttr("style");
            copy.removeClass("template");
            document.select("tr[class=vacancy]").remove().not("tr[class=vacancy template");

            vacancies.forEach(vacancy -> {
                Element localClone = copy.clone();
                localClone.getElementsByClass("city").first().text(vacancy.getCity());
                localClone.getElementsByClass("companyName").first().text(vacancy.getCompanyName());
                localClone.getElementsByClass("salary").first().text(vacancy.getSalary());
                Element link =localClone.getElementsByTag("a").first();
                link.text(vacancy.getTitle());
                link.attr("href", vacancy.getUrl());
                template.before(localClone.outerHtml());
            });
        } catch (IOException e) {
            e.printStackTrace();
            return "Some exception occurred";
        }
        return document.html();
    }

    private void updateFile(String updatedFileContent) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(updatedFileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

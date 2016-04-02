package alainvanhout.demo.services;

import alainvanhout.demo.entities.Person;
import alainvanhout.demo.Template;
import alainvanhout.demo.renderers.PersonRenderer;
import renderering.core.Renderer;
import renderering.core.basic.StringRenderer;
import renderering.web.html.basic.documentbody.list.UnorderedListRenderer;
import renderering.core.retrieve.FetchingRenderer;
import alainvanhout.demo.repositories.PersonRepository;
import alainvanhout.cms.repositories.TemplateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class RendererService implements FetchingRenderer.Fetcher<String> {
    @Autowired
    private TemplateRepository templateRepository;

    @Autowired
    private PersonRepository personRepository;

    @Override
    public Renderer fetch(String templateName) {
        switch (templateName) {
            case "persons":
                UnorderedListRenderer list = new UnorderedListRenderer();
                Collection<Person> people = personRepository.findAll();
                for (Person person : people) {
                    list.add(new PersonRenderer(fetch("person-small")).set(person));
                }
                return list;
        }

        Template template = templateRepository.findByName(templateName);
        if (template == null) {
            return null;
        } else {
            return new StringRenderer(template.getBody());
        }
    }
}
package com.obu.tech.poba.published_info;

import com.obu.tech.poba.utils.search.SearchConditionBuilder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import java.util.List;
import static com.obu.tech.poba.utils.search.SearchOperator.*;

@Service
public class PublishedService {
    @Autowired
    private PublishedRepository publishedRepository;

    @Autowired
    private PublishedJoinRepository publishedJoinRepository;

    public  List<Published> findBySearchCriteria(Published published){
        return publishedRepository.findAll(new SearchConditionBuilder<Published>()
                .ifNotNullThenAnd("fullNamePublisher", LIKE, published.getName().replaceAll("\\s", ""))
                .ifNotNullThenOr("fullNameJoiner", LIKE, published.getName().replaceAll("\\s", ""))
                .ifNotNullThenAnd("publishedLevel", LIKE, published.getPublishedLevel())
                .build());
    }

    public  Published findPublishedById(String id){
        if (id.matches("\\d+")) {
            return publishedRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        } else {
            System.out.println("Invalid published_id: '" + id + "'");
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    public  List<PublishedJoin> findPublishedJoinById(Long id){
        return publishedJoinRepository.findByPublishedId(id);
    }

    public Published savePublished(PublishedDto publishedDto){
        Published published = new Published();
        BeanUtils.copyProperties(publishedDto,published);
        String prefix = published.getPrefix();
        if("อื่นๆ".equals(prefix)){
            prefix = published.getPrefixOther();
        }
        published.setFullNamePublisher(prefix+published.getName()+published.getSurname());

        String prefixJoiner = publishedDto.getPublishedJoinPrefix();
        if("อื่นๆ".equals(prefixJoiner)){
            prefixJoiner = publishedDto.getPublishedJoinPrefixOther();
        }
        published.setFullNameJoiner(prefixJoiner+publishedDto.getPublishedJoinName()+publishedDto.getPublishedJoinSurname());

        return  publishedRepository.saveAndFlush(published);
    }

    public PublishedJoin savePublishedJoin(PublishedDto publishedDto){
        PublishedJoin publishedJoin = new PublishedJoin();
        BeanUtils.copyProperties(publishedDto,publishedJoin);
        publishedJoin.setPrefix(publishedDto.getPublishedJoinPrefix());
        publishedJoin.setPrefixOther(publishedDto.getPublishedJoinPrefixOther());
        publishedJoin.setName(publishedDto.getPublishedJoinName());
        publishedJoin.setSurname(publishedDto.getPublishedJoinSurname());
//        Double fund = 0.00;
//        if(!StringUtils.isBlank(publishedDto.getPublishedFund())){
//           String fundStr = publishedDto.getPublishedFund().replace(",","");
//           fund = Double.parseDouble(fundStr);
//        }
//        publishedJoin.setPublishedFund(fund);
        return publishedJoinRepository.saveAndFlush(publishedJoin);
    }

}

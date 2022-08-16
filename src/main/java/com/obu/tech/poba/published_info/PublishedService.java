package com.obu.tech.poba.published_info;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import java.util.ArrayList;
import java.util.List;

@Service
public class PublishedService {
    @Autowired
    private PublishedRepository publishedRepository;

    @Autowired
    private PublishedJoinRepository publishedJoinRepository;

    @Autowired
    private FiscalYearRepository fiscalYearRepository;

    public  List<Published> findBySearchCriteria(Published published){
        List<Object[]> dataList = publishedRepository.findPublishedInfo("%"+published.getName()+"%",published.getPublishedLevel(),published.getPublishedYear2());

        List<Published> publisheds = new ArrayList<>();
        if (!dataList.isEmpty() && dataList.size() >0){
            for(final Object[] e : dataList){
                final Published result = new Published();
                result.setPublishedId(Long.parseLong(e[0].toString()));
                result.setPrefix( !e[1].toString().equals("อื่นๆ")? e[1].toString() : e[2].toString());
                result.setName(e[3].toString()+" "+e[4].toString());
                if (e[5] != null ) {
                    result.setPublishedStatus(e[5].toString());
                }

                if(e[6] != null){
                    result.setPublishedTopic(e[6].toString());
                }

                if(e[7] != null){
                    result.setPublishedLevel(e[7].toString());
                }

                publisheds.add(result);
            }
        }
        return publisheds;
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
        return publishedJoinRepository.saveAndFlush(publishedJoin);
    }

    public void removeByPublishedId(Long id){
        fiscalYearRepository.deleteByPublishedId(id);
    }

    public List<FiscalYear> saveFiscalYear(List<FiscalYear> fiscalYears){
        return  fiscalYearRepository.saveAllAndFlush(fiscalYears);
    }

    public  List<FiscalYear>findFiscalYearByPublishedId(long id){
        return fiscalYearRepository.findByPublishedId(id);
    }

}

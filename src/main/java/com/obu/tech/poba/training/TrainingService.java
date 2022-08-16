package com.obu.tech.poba.training;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TrainingService {
    @Autowired
    TrainingRepository trainingRepository;
    @Autowired
    TrainingPhaseRepository trainingPhaseRepository;

    List<Training> findBySearchCriteria(Training training){
        List<Object[]> data = trainingRepository.findInfo("%"+training.getName1()+"%",
                training.getTrainingLevel(),
                training.getTrainingDateFrom(),
                training.getTrainingDateTo());

        List<Training> trainings = new ArrayList<>();
        if (!data.isEmpty() && data.size() >0){
            for(final Object[] e : data){
                final Training result = new Training();
                result.setTrainingId(Long.parseLong(e[0].toString()));
                result.setPrefix1( !e[1].toString().equals("อื่นๆ")? e[1].toString() : e[2].toString());
                result.setName1(e[3].toString()+" "+e[4].toString());
                if (e[5] != null ) {
                    result.setTrainingStatus1(e[5].toString());
                }

                if(e[6] != null){
                    result.setTrainingLevel(e[6].toString());
                }

                trainings.add(result);
            }
        }
        return trainings;
    }

    public Training save(Training training) {
        return trainingRepository.saveAndFlush(training);
    }

    public Training findById(String id) {
        if (id.matches("\\d+")) {
            return trainingRepository.findById(Long.parseLong(id))
                    .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        } else {
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    public List<TrainingPhase> savePhase(List<TrainingPhase> phases){
        return  trainingPhaseRepository.saveAllAndFlush(phases);
    }

    public List<TrainingPhase> findByTrainingId(String id){
        if (id.matches("\\d+")) {
            return trainingPhaseRepository.findByTrainingId(Long.parseLong(id));
        } else {
            System.out.println("Invalid trainingId: '" + id + "'");
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    public List<TrainingPhase> removePhase(long phaseId,long trainingId){
        trainingPhaseRepository.deleteById(phaseId);
        return trainingPhaseRepository.findByTrainingId(trainingId);
    }
}

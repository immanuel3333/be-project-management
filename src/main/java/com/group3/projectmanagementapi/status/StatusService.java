package com.group3.projectmanagementapi.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.group3.projectmanagementapi.status.model.Status;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Service
@Getter
@Setter
@AllArgsConstructor
public class StatusService {
    private StatusRepository statusRepository;

    public List<Status> getDefaultStatus() {
        List<Status> defaultStatusList = new ArrayList<>();
    
        Optional<Status> status1 = statusRepository.findById((long) 1);
        Optional<Status> status2 = statusRepository.findById((long) 2);
        Optional<Status> status3 = statusRepository.findById((long) 3);
    
        if (!status1.isPresent()) {
            Status newStatus = Status.builder().status("Todo").build();
            status1 = Optional.of(statusRepository.save(newStatus));
        }
        status1.ifPresent(defaultStatusList::add);
    
        if (!status2.isPresent()) {
            Status newStatus = Status.builder().status("In Dev").build();
            status2 = Optional.of(statusRepository.save(newStatus));
        }
        status2.ifPresent(defaultStatusList::add);
    
        if (!status3.isPresent()) {
            Status newStatus = Status.builder().status("Done").build();
            status3 = Optional.of(statusRepository.save(newStatus));
        }
        status3.ifPresent(defaultStatusList::add);
    
        return defaultStatusList;
    }
    

    public Status create(String name) {
        Status newStatus = Status.builder().status(name).build();
        return this.statusRepository.save(newStatus);
    }
        
}

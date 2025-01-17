package com.bcp.qrcode.services.impl;

import com.bcp.qrcode.entities.Rib;
import com.bcp.qrcode.repo.RibRepository;
import com.bcp.qrcode.services.RibService;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RibServiceImpl implements RibService {

    private final RibRepository ribRepository;

    public RibServiceImpl(RibRepository ribRepository) {
        this.ribRepository = ribRepository;
    }

    @Override
    public Map<String, Object> getUserRibData(Long userId) {
        // Fetch the RIB of the user from the database
        Rib rib = ribRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("No RIB found for user with ID: " + userId));

        // Create and return the JSON object
        return Map.of(
                "user_id", rib.getUser().getId(),
                "username", rib.getUser().getUsername(),
                "rib", rib.getRibNumber()
        );
    }
}

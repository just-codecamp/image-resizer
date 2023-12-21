package gg.scode.imageresizeservice.dto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@NoArgsConstructor
public class CacheVo {

    private String data;
    private LocalDateTime lastModified;

    @Builder
    public CacheVo(String data, LocalDateTime lastModified) {
        this.data = data;
        this.lastModified = lastModified;
    }

}

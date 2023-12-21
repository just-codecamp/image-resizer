package gg.scode.imageresizeservice.services.interfaces;

import gg.scode.imageresizeservice.dto.ImageStandard;

public interface Resizable {

    byte[] resize(ImageStandard imageStandard) throws Exception;

}

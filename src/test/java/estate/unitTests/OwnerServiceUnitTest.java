package estate.unitTests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import estate.dao.OwnerDao;
import estate.domain.Owner;
import estate.dto.OwnerDto;
import estate.exception.OwnerNotFoundException;
import estate.service.OwnerService;
import static org.junit.jupiter.api.Assertions.assertThrows;


@ExtendWith(MockitoExtension.class)
public class OwnerServiceUnitTest {

    @Spy
    @InjectMocks
    private OwnerService ownerService= new OwnerService();

    @Mock
    private OwnerDao ownerDao;

    @Test
    public void whenOwnerExists_thenRetrieveOwnerDto(){
        Mockito.when(ownerDao.findByPersonalCode("49105170123")).thenReturn(new Owner());

        OwnerDto ownerDto = ownerService.getOwnerByPersonalCode("49105170123");
        Assertions.assertNotNull(ownerDto);
    }

    @Test
    public void whenOwnerDoesNotExist_thenThrowException(){
        Mockito.when(ownerDao.findByPersonalCode("49105170123")).thenReturn(null);

        Exception exception = assertThrows(OwnerNotFoundException.class, () -> {
            ownerService.getOwnerByPersonalCode("49105170123");
        });

        Assertions.assertEquals(exception.getMessage(), "Owner with personal code 49105170123 not found");
    }

}

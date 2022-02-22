package org.escorpion;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class MockitoTests { 

    @Mock
    List<String> lista;


    @Test
    public void primeiroTesteMockito(){
        Mockito.when(lista.size()).thenReturn(2);

        lista.size();
        lista.add("");
        

        Mockito.verify(lista, Mockito.times(1)).size();
        Mockito.verify(lista, Mockito.never()).get(1);

        InOrder inOrder = Mockito.inOrder(lista);
        inOrder.verify(lista).size();
        inOrder.verify(lista).add("");
    }
}

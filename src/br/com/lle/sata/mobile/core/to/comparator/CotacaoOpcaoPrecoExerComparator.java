package br.com.lle.sata.mobile.core.to.comparator;

import java.math.BigDecimal;
import java.util.Comparator;

import br.com.lle.sata.mobile.core.to.CotacaoOpcaoTO;

public class CotacaoOpcaoPrecoExerComparator implements Comparator<CotacaoOpcaoTO> {

	@Override
	public int compare(CotacaoOpcaoTO o1, CotacaoOpcaoTO o2) {
		BigDecimal val1 = new BigDecimal(o1.getPrecoExercicio());
		BigDecimal val2 = new BigDecimal(o2.getPrecoExercicio());
		// compara o preco de exercicio
		return val1.compareTo(val2);
	}

}

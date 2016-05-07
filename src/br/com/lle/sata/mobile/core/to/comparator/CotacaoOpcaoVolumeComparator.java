package br.com.lle.sata.mobile.core.to.comparator;

import java.util.Comparator;

import br.com.lle.sata.mobile.core.to.CotacaoOpcaoTO;

public class CotacaoOpcaoVolumeComparator implements Comparator<CotacaoOpcaoTO> {

	@Override
	public int compare(CotacaoOpcaoTO o1, CotacaoOpcaoTO o2) {
		Integer val2 = Integer.parseInt(o2.getVolume());
		Integer val1 = Integer.parseInt(o1.getVolume());
		// compara o volume do segundo em relacao ao primeiro
		// para ficar em ordem decrescente
		return val2.compareTo(val1);
	}

}

package br.com.lle.sata.mobile.core.interfaces;

import java.util.List;

import br.com.lle.sata.mobile.core.to.CotacaoOpcaoTO;

public interface IBuscaCotacaoOpcao {
	
	public List<CotacaoOpcaoTO> getCotacoesOpcoes(String codigoAtivo, boolean ehCall);

}

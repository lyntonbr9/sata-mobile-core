package br.com.lle.sata.mobile.core.robo;

import static br.com.lle.sata.util.LogUtil.log;
import static br.com.lle.sata.util.StringUtil.concat;

import java.util.Hashtable;

import br.com.lle.sata.mobile.core.interfaces.IBuscaCotacao;
import br.com.lle.sata.mobile.core.to.CotacaoAtivoTO;
import br.com.lle.sata.util.http.HTTPSata;

@Deprecated
public class BVMFBuscaCotacao implements IBuscaCotacao {
	
	// quoteElementPiece6 - ultimo
	// quoteElementPiece7 - maxima
	// quoteElementPiece8 - minima
	// quoteElementPiece9 - abertura
	// quoteElementPiece10 - fechamento
	// quoteElementPiece17 - volume

	private String URL_COTACAO = "http://www.bmfbovespa.com.br/Pregao-Online/ExecutaAcaoAjax.asp?CodigoPapel=";
	
	@Override
	public String getCotacao(String codigo) {
		
		Hashtable<String, String> h = new Hashtable<String, String>();
		String url = concat(URL_COTACAO, codigo.toUpperCase());
		String html = HTTPSata.GET(url, h);	
		// recupera a ultima cotacao
		return getValor(html, "Ultimo");
	}
	
	public CotacaoAtivoTO getCotacaoAtivo(String codigo) {
		CotacaoAtivoTO cotacaoAtivo = new CotacaoAtivoTO();
		/*
		String url = "http://br.advfn.com/bolsa-de-valores/bovespa/" + codigo.toUpperCase() +"/cotacao";
		String html = HTTPSata.GET(url, null);
		// quoteElementPiece7 - maxima
		cotacaoAtivo.setMaxima(getValor(html, "quoteElementPiece7").replace(",", ""));
		// quoteElementPiece8 - minima
		cotacaoAtivo.setMinima(getValor(html, "quoteElementPiece8").replace(",", ""));
		// quoteElementPiece9 - abertura
		cotacaoAtivo.setAbertura(getValor(html, "quoteElementPiece9").replace(",", ""));
		// quoteElementPiece10 - fechamento
		cotacaoAtivo.setFechamento(getValor(html, "quoteElementPiece10").replace(",", ""));
		// codigo
		cotacaoAtivo.setCodigo(codigo);
		// TODO ver este split
		cotacaoAtivo.setSplit(1);
		// periodo
		Calendar cal = GregorianCalendar.getInstance();
		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		String periodo = sd.format(cal.getTime());
		cotacaoAtivo.setPeriodo(periodo);
		// ano
		sd.applyPattern("yyyy");
		cotacaoAtivo.setAno(sd.format(cal.getTime()));
		// volume
		cotacaoAtivo.setVolume(getValor(html, "quoteElementPiece17").replace(".", ""));
		// tipo periodo
		cotacaoAtivo.setTipoPeriodo("D");
		*/
		return cotacaoAtivo;
	}
	
	private String getValor(String html, String atributo) {
		int corte = html.indexOf(atributo);
		if (corte < 0) {
			return "9999,99";
		} else {
			html = html.substring(corte);

			int inicio = html.indexOf("\"");
			int fim = html.indexOf("\"", inicio + 1);

			return html.substring(inicio + 1, fim).trim();
		}
	}
	
	public static void main(String[] args) {
		BVMFBuscaCotacao bc = new BVMFBuscaCotacao();
		String cotacao = bc.getCotacao("PETRA12");
		log(cotacao);
	}
	
}

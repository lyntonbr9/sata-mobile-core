package br.com.lle.sata.mobile.core.robo;

import static br.com.lle.sata.util.LogUtil.log;
import static br.com.lle.sata.util.StringUtil.concat;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;

import br.com.lle.sata.mobile.core.http.HTTPSata;
import br.com.lle.sata.mobile.core.interfaces.IBuscaCotacao;
import br.com.lle.sata.mobile.core.to.CotacaoAtivoTO;

public class BuscaCotacao implements IBuscaCotacao {
	
	// quoteElementPiece6 - ultimo
	// quoteElementPiece7 - maxima
	// quoteElementPiece8 - minima
	// quoteElementPiece9 - abertura
	// quoteElementPiece10 - fechamento
	// quoteElementPiece17 - volume

	@Override
	public String getCotacao(String codigo) {
		
		Hashtable<String, String> h = new Hashtable<String, String>();
//		String url = "http://br.advfn.com/bolsa-de-valores/bovespa/" + codigo.toUpperCase() +"/cotacao";
		String url = concat("http://br.advfn.com/bolsa-de-valores/bovespa/", codigo.toUpperCase(), "/cotacao");
		String html = HTTPSata.GET(url, h);	
		// recupera a ultima cotacao - quoteElementPiece6
		return getValor(html, "quoteElementPiece6");
	}
	
	public CotacaoAtivoTO getCotacaoAtivo(String codigo) {
		CotacaoAtivoTO cotacaoAtivo = new CotacaoAtivoTO();
		String url = concat("http://br.advfn.com/bolsa-de-valores/bovespa/", codigo.toUpperCase(), "/cotacao");
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
		return cotacaoAtivo;
	}
	
	private String getValor(String html, String spanId) {
		int corte = html.indexOf(spanId);
		if (corte < 0) {
			return "9999,99";
		} else {
			html = html.substring(corte);

			int inicio = html.indexOf(">");
			int fim = html.indexOf("</span>", inicio);

			return html.substring(inicio + ">".length(), fim).trim();
		}
	}
	
	public static void main(String[] args) {
		BuscaCotacao bc = new BuscaCotacao();
		String cotacao = bc.getCotacao("PETRH20");
		log(cotacao);
	}
	
}

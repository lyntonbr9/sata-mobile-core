package br.com.lle.sata.mobile.alert;

import static br.com.lle.sata.util.LogUtil.log;
import static br.com.lle.sata.util.StringUtil.concat;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import br.com.lle.sata.alert.domain.AlertaStop;
import br.com.lle.sata.alert.domain.AvisoStop;
import br.com.lle.sata.alert.domain.StopAtivo;
import br.com.lle.sata.alert.domain.UsuarioAlert;
import br.com.lle.sata.alert.interfaces.INotificacao;
import br.com.lle.sata.alert.monitor.VerificaStop;
import br.com.lle.sata.mobile.Content;
import br.com.lle.sata.mobile.POST2GCM;

@Deprecated
public class Notificacao implements INotificacao {

	private final static String SERVER_API_KEY = "AIzaSyAJkI3UGZ2u6hlj3uAIpPFhnJ4X29q5bgw";
    
	private static int HORA_COMECO_BVMF = 10; // as 10 horas comeca
	private static int HORA_FIM_BVMF = 17; // termina as 17 hs
	private static int QUINZE_MIN_MILIS = 15*60*1000;
	
	@Override
	public void notificar(UsuarioAlert usuario, String msg) {
    	Content content = createContent(usuario, msg);
    	
        POST2GCM.post(SERVER_API_KEY, content);
    }

    public static Content createContent(UsuarioAlert usuario, String msg){
        Content c = new Content();
        
        c.addRegId(usuario.getRegId());
        
        c.createData(msg, msg);

        return c;
    }
    
    public static List<String> getAlertMessages(List<AlertaStop> alertas) {
    	log("Vai recuperar as msgens de alerta");
    	List<String> msgs = new ArrayList<String>();
    	AvisoStop as = new AvisoStop();
    	as.setStopsAtivo(new ArrayList<StopAtivo>());
    	for (AlertaStop als : alertas) {
			StopAtivo sa = new StopAtivo(als.getCodigoAtivo(), als.getPrecoStopLow(), als.getPrecoStopHigh(), als.isHabilitado());
			as.getStopsAtivo().add(sa);
		}
    	VerificaStop vs = new VerificaStop();
    	vs.verificarNotificacao(as);
		for (StopAtivo sa : as.getStopsAtivo()) {
			if (sa.getMaxDisparo().isNotificar()) {
				msgs.add(sa.getMaxDisparo().getMensagem());
			}
			if (sa.getMinDisparo().isNotificar()) {
				msgs.add(sa.getMinDisparo().getMensagem());
			}
		}
    	return msgs;
    }
    
    public static void main( String[] args )
    {
        log( "Monitorando STOPs..." );
        INotificacao notif = new Notificacao();
        VerificaStop vs = new VerificaStop();
        List<AvisoStop> ass = carregarAvisosStop();
        // recupera a data atual
        Calendar dataAtual = Calendar.getInstance();
        SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		int i = 30;
		// no loop
		while (i > 0) {
			try {
				int horaAtual = dataAtual.get(Calendar.HOUR_OF_DAY);
//				if (horaAtual >= HORA_COMECO_BVMF && horaAtual < HORA_FIM_BVMF) {
					// recupera a data atual
					dataAtual = Calendar.getInstance();
					log(concat("Data/hora COTACOES: ", sd.format(dataAtual.getTime())));
					vs.verificarNotificacao(ass);
					for(AvisoStop as : ass) {
						for (StopAtivo sa : as.getStopsAtivo()) {
							if (sa.getMaxDisparo().isNotificar()) {
								notif.notificar(as.getUsuario(), sa.getMaxDisparo().getMensagem());
							}
							if (sa.getMinDisparo().isNotificar()) {
								notif.notificar(as.getUsuario(),  sa.getMinDisparo().getMensagem());
							}
						}
					}
					i--; // desconta do contador caso tenha executado uma busca de cotacao
//				}
				Thread.sleep(QUINZE_MIN_MILIS); // a thread descansa por 15 min
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}
    }
    
    public static List<AvisoStop> carregarAvisosStop() {
		List<AvisoStop> avisosStop = new ArrayList<AvisoStop>();
		// usuario lynton
		AvisoStop avisoStop = new AvisoStop();
		UsuarioAlert usuario = new UsuarioAlert();
		usuario.setLogin("lyntonbr");
//		usuario.setRegId("APA91bEwNmmddBDT2W4no_pPwgKXwQoLC0OXzNdaO2H70eoDHMlJ2D24kJtL5WyGJpaYgxw9KuoGbbKXG70mBsp-s3zmHyyFf5_DVK1mp03LvYXnlgS8C1hw01Z8fY_oaB_Q2NnNLfK0rgFpdIAx8CM3XFV_KDQ1rA");
		usuario.setRegId("APA91bEStrVI65Qf8awLjXdAX1VLcmaEpVNAorWF_ZyWC-28txG-EG-bAiLANV4K2oPPyZIrHWBh0-eoL9e7n3bMMzKdE2oTOs7Dco_zDTeExCmEawzE368CRVBqriEaAFBLdxkht9fiSphe-EENecK6B9teVM4Z_Q");
		List<StopAtivo> stopsAtivo = new ArrayList<StopAtivo>();
        stopsAtivo.add(new StopAtivo("PETR4", "12.20", "13.75", true));
        stopsAtivo.add(new StopAtivo("VALE5", "16.40", "19.28", true));
        avisoStop.setUsuario(usuario);
        avisoStop.setStopsAtivo(stopsAtivo);
        avisosStop.add(avisoStop);
        
        //usuario flavio
        avisoStop = new AvisoStop();
        usuario = new UsuarioAlert();
		usuario.setLogin("flaviogc");
		usuario.setRegId("APA91bHwdUbE0Ks6j4vMkn9DMueq8TFa9MdaaWnipWUapfL9Ri9frfWhR_dD-t4UzhyDIWGo5ONL6nfozaISf1RyKNDgOGpIgj-xI08ZujAlYTJNoetfh6CCP21NiW56YsEnYFIwwPLQBOYAYtjP6YvV0ynt4PIXQw");
		stopsAtivo = new ArrayList<StopAtivo>();
        avisoStop.setUsuario(usuario);
        avisoStop.setStopsAtivo(stopsAtivo);
//        avisosStop.add(avisoStop);
        
		return avisosStop;
	}

	@Override
	public void notificar(UsuarioAlert usuario, String titulo, String msg) {
		// TODO Auto-generated method stub
		
	}

}

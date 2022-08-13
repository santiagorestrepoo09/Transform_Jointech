package Inicio;

import java.security.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

public class Transformada<ObjetoCaracter>  extends Main {
	
	public void setDatos(String trama) {

		System.out.println("----------------------------------------------------");
		
		String s = trama; 
		s = s.replaceAll("7D01","7D");
		s = s.replaceAll("7D02","7E");
		System.out.println("TRAMA SIN ESCAPE \n" + s );
		
		System.out.println("Caracteres de la trama = " + s.length());
		this.parseInsertRecord_JOINTECH(s);
		
	}
	
		private LinkedHashMap<String, String> generateHashMapValues(String caracteres, String valor) {
			LinkedHashMap<String, String> valores = new LinkedHashMap<String, String>();
			valores.put("caracteres", caracteres);
			valores.put("valor", valor);
			return valores;
		}

			private byte[] parseInsertRecord_JOINTECH(String s){	
				int Datalength = 01;
				int Datalength02 = 02;
				double Bateria = 0.0;
				float Voltaje = 0;
				double GmsSenal = 0;
				int Satelites = 0;
				int STATUS_CODE = 0 ;
				String VersionRed = "";
				String VersionProtocolo2 = "";
				String Recorrido = "";
				String Identificador = "";
				String Identificador2 = "";
				String ByteStatus = "";
				
				int AnchoConvertTramaAdicional = 0;
			
				String ConvertTrama = s.substring(82 , s.length() ) ;
				
				String ConvertTramaAdicional = ConvertTrama ; 
				System.out.println("------------------------------------------------------------------------------------");
				//System.out.println("ConvertTrama = " + ConvertTrama );
				//System.out.println("ConvertTramaAdicional = " + ConvertTramaAdicional );
				
				if( ConvertTramaAdicional.startsWith("D4") ){
					int AnchoPorBateria = Integer.parseInt(ConvertTramaAdicional.substring(2 , 4)) ;
					if ( AnchoPorBateria == Datalength ) {
						Bateria = Math.round(Integer.parseInt(ConvertTramaAdicional.substring(4,6) , 16));
						AnchoConvertTramaAdicional = ConvertTramaAdicional.length();
						ConvertTramaAdicional = ConvertTramaAdicional.substring(6 , AnchoConvertTramaAdicional);
					}					
					
					System.out.println("Bateria = " + Bateria);
				}
				
				if( ConvertTramaAdicional.startsWith("D5") ){
					int AnchoPorVoltaje= Integer.parseInt(ConvertTramaAdicional.substring(2 , 4)) ;
					if ( AnchoPorVoltaje == Datalength ) {
						Voltaje = Integer.parseInt(ConvertTramaAdicional.substring(4,6) , 16) ;
						Voltaje = Voltaje /100;
						AnchoConvertTramaAdicional = ConvertTramaAdicional.length();
						ConvertTramaAdicional = ConvertTramaAdicional.substring(6 , AnchoConvertTramaAdicional);
					}else if(AnchoPorVoltaje == Datalength02) {
						Voltaje =  Integer.parseInt(ConvertTramaAdicional.substring(4,8) , 16)  ;
						Voltaje = Voltaje /100;
						AnchoConvertTramaAdicional = ConvertTramaAdicional.length();
						ConvertTramaAdicional = ConvertTramaAdicional.substring(8 , AnchoConvertTramaAdicional);
					}
					System.out.println("Voltaje = " + Voltaje);
				}
				if( ConvertTramaAdicional.startsWith("30") ){
					int AnchoPorGmsSeñal= Integer.parseInt(ConvertTramaAdicional.substring(2 , 4)) ;
					if ( AnchoPorGmsSeñal == Datalength ) {
						GmsSenal = Integer.parseInt(ConvertTramaAdicional.substring(4,6) , 16);
						AnchoConvertTramaAdicional = ConvertTramaAdicional.length();
						ConvertTramaAdicional = ConvertTramaAdicional.substring(6 , AnchoConvertTramaAdicional);
					}else if(AnchoPorGmsSeñal == Datalength02) {
						GmsSenal = Integer.parseInt(ConvertTramaAdicional.substring(4,8) , 16);
						AnchoConvertTramaAdicional = ConvertTramaAdicional.length();
						ConvertTramaAdicional = ConvertTramaAdicional.substring(8 , AnchoConvertTramaAdicional);
					}
					System.out.println("GmsSenal = " + GmsSenal);
				}
				if( ConvertTramaAdicional.startsWith("31") ){
					int AnchoPorSatelites= Integer.parseInt(ConvertTramaAdicional.substring(2 , 4)) ;
					if ( AnchoPorSatelites == Datalength ) {
						Satelites = Integer.parseInt(ConvertTramaAdicional.substring(4,6) , 16);
						AnchoConvertTramaAdicional = ConvertTramaAdicional.length();
						ConvertTramaAdicional = ConvertTramaAdicional.substring(6 , AnchoConvertTramaAdicional);
					}else if(AnchoPorSatelites == Datalength02) {
						Satelites = Integer.parseInt(ConvertTramaAdicional.substring(4,8) , 16);
						AnchoConvertTramaAdicional = ConvertTramaAdicional.length();
						ConvertTramaAdicional = ConvertTramaAdicional.substring(8 , AnchoConvertTramaAdicional);
					}
					System.out.println("Satelites = " + Satelites);
				}
				if( ConvertTramaAdicional.startsWith("F4") ){
					VersionRed = ConvertTramaAdicional.substring(2 , 6) ;
					System.out.println("VersionRed = " + VersionRed);
					AnchoConvertTramaAdicional = ConvertTramaAdicional.length();
					ConvertTramaAdicional = ConvertTramaAdicional.substring(6 , AnchoConvertTramaAdicional);
				}
				if( ConvertTramaAdicional.startsWith("F9") ){
					String DataCaracteres [] = BusquedaCaracteres(ConvertTramaAdicional,"FE");
					VersionProtocolo2 = DataCaracteres[1];
					ConvertTramaAdicional = DataCaracteres[0];
					System.out.println("VersionProtocolo2 = " + VersionProtocolo2);
				}
				if( ConvertTramaAdicional.startsWith("FE") ){
					String DataCaracteres [] = BusquedaCaracteres(ConvertTramaAdicional,"FD");
					Recorrido = DataCaracteres[1];
					ConvertTramaAdicional = DataCaracteres[0];
					System.out.println("Recorrido = " + Recorrido);
				}
				if( ConvertTramaAdicional.startsWith("FD") ){
					String DataCaracteres [] = BusquedaCaracteres(ConvertTramaAdicional,"EF");
					Identificador = DataCaracteres[1];
					ConvertTramaAdicional = DataCaracteres[0];
					System.out.println("Identificador = " + Identificador);
				}
				if( ConvertTramaAdicional.startsWith("EF") ){
					String DataCaracteres [] = BusquedaCaracteres(ConvertTramaAdicional,"0B");
					Identificador2 = DataCaracteres[1];
					ConvertTramaAdicional = DataCaracteres[0];
					System.out.println("Identificador2 = " + Identificador2);
				}
				if( ConvertTramaAdicional.startsWith("0B") ){
					String DataCaracteres [] = BusquedaCaracteres(ConvertTramaAdicional,"FC");
					ByteStatus =  DataCaracteres[1].substring(0 , 6);
					ConvertTramaAdicional = DataCaracteres[0];
					System.out.println("ByteStatus = " + ByteStatus);
				}
				
				 HashMap<String,Integer> statusCode = new HashMap<String,Integer>();  
					statusCode.put("0B0801",45073);
					statusCode.put("0B0802",45074);
					statusCode.put("0B0803",45075);
					statusCode.put("0B0805",45076);
					statusCode.put("0B081E",45077);
					statusCode.put("0B081F",45078);
					statusCode.put("0B0706",45079);
					statusCode.put("0B0707",45080);
					statusCode.put("0B0708",45081);
					statusCode.put("0B0710",45088);
					statusCode.put("0B0711",45089);
					statusCode.put("0B0718",45090);
					statusCode.put("0B0719",45091);
					statusCode.put("0B0720",45092);
					statusCode.put("0B0728",45093);
					statusCode.put("0B0729",45094);
					statusCode.put("0B0622",45095);
					statusCode.put("0B0523",45096);
					statusCode.put("0B052A",45097);
					statusCode.put("0B052B",45104);
					statusCode.put("0B010B",45105);
					statusCode.put("0B010C",45106);
					statusCode.put("0B010D",45107);
					statusCode.put("0B0112",45108);
					statusCode.put("0B011C",45109);
				
				for (Entry<String, Integer> entry : statusCode.entrySet()) {
					if ( entry.getKey().equalsIgnoreCase(ByteStatus) == true) {
						STATUS_CODE = entry.getValue();
					}
				}
				
				System.out.println("STATUS_CODE = " + STATUS_CODE);
				
				
				System.out.println("\n\nLinkedHashMap");
				LinkedHashMap<String, LinkedHashMap<String, String>> indicador = new LinkedHashMap<String, LinkedHashMap<String, String>>();
				indicador.put("Encabezado", generateHashMapValues("2", ""));
				indicador.put("Idmensaje",  generateHashMapValues("4", ""));
				indicador.put("Atributos",  generateHashMapValues("4", ""));
				indicador.put("NumeroIdentificacion",  generateHashMapValues("12", ""));
				indicador.put("MensajeSerie",  generateHashMapValues("4", ""));
				indicador.put("Alarma",  generateHashMapValues("8", ""));
				indicador.put("Estado",  generateHashMapValues("8", ""));
				indicador.put("Latitud",  generateHashMapValues("8", ""));
				indicador.put("Longitud",  generateHashMapValues("8", ""));
				indicador.put("Altitud",  generateHashMapValues("4", ""));
				indicador.put("Velocidad",  generateHashMapValues("4", ""));
				indicador.put("Direccion",  generateHashMapValues("4", ""));
				indicador.put("Fecha",  generateHashMapValues("6", ""));
				indicador.put("Tiempo",  generateHashMapValues("6", ""));
				indicador.put("Bateria",  generateHashMapValues("6", ""));
				indicador.put("BateriaVoltage",  generateHashMapValues("8", ""));
				indicador.put("intensidad",  generateHashMapValues("6", ""));
				indicador.put("NumeroSatelites",  generateHashMapValues("6", ""));
				indicador.put("VersionProtocolo",  generateHashMapValues("8", ""));
				indicador.put("Odometro",  generateHashMapValues("12", ""));
	
	
				int acumulador = 0;
				int acumuladorAnterior = 0;
				String tramaPorcionada = "";
				int auxiliarLlenadoEnetros = 0 ;
				float auxiliarLlenadoFlotantes = 0 ;
	
				for(Map.Entry<String, LinkedHashMap<String, String>> k: indicador.entrySet()) {
					tramaPorcionada = "";
					for(Map.Entry<String, String> j:  k.getValue().entrySet()) {
						if(j.getKey() == "caracteres") {
							acumulador += Integer.parseInt(j.getValue());
							tramaPorcionada = s.substring(acumuladorAnterior, acumulador);
							acumuladorAnterior = acumulador;
						}
						if(j.getKey() == "valor") {
							j.setValue(tramaPorcionada);
						}
					}
				}
				//System.out.println("Caracteres viejos ------------------");
				//System.out.println(indicador);
	
					/* Parsing */
					double  Latitud2 	  			= 0.0;
					double  longitude2				= 0.0;
					double  altitudeM				= 0.0; 
					double 	headingDeg				= 0.0; 
					double  speedKPH2    			= 0.0;
					double  odomKM2      			= 0.0;
					int     numSats        			= 0;	
					double  batteryLevel2			= 0.0;
					double  batteryVolts2 			= 0.0;
					double  signalStrength2			= 0;
					int  	satelliteCount2			= 0;
					int		mobileCountryCode2 		= 0;
					int 	mobileNetworkCode2 		= 0;
					int  	cellTowerID2   			= 0;
					int 	locationAreaCode2		= 0;
					int 	gpsFixStatus    		= 0 ; 
					int VersionProtocolo = 0;
					String TramaExtendida = "";
					
					String Fecha = "";
					String Tiempo = "";
					String 	numHex 					= "" ;
					
				System.out.println("---------------------------------------------------------------- ");
				
				String mobileID 	= "" ;
				long    fixtime   = 0 ;
	
				for(Map.Entry<String, LinkedHashMap<String, String>> a: indicador.entrySet()) {
					for(Map.Entry<String, String> j:  a.getValue().entrySet()) {
						if (j.getKey() == "valor" && a.getKey() == "Atributos") {
							System.out.println( a.getKey() + " -> " +j.getValue());
							auxiliarLlenadoEnetros = Integer.parseInt(j.getValue(),16);
							j.setValue(Integer.toString(auxiliarLlenadoEnetros));
						}
						if (j.getKey() == "valor" && a.getKey() == "NumeroIdentificacion") {
							System.out.println( a.getKey() + " -> " +j.getValue());
							mobileID = j.getValue();
						}
						if (j.getKey() == "valor" && a.getKey() == "MensajeSerie") {
							System.out.println( a.getKey() + " -> " + j.getValue());
							auxiliarLlenadoEnetros = Integer.parseInt(j.getValue(),16);
							j.setValue(Integer.toString(auxiliarLlenadoEnetros));
						}
						if (j.getKey() == "valor" && a.getKey() == "Estado") {
							System.out.println( a.getKey() + " -> " + j.getValue());
							numHex = j.getValue().substring(7,8);
							j.setValue(numHex);
						}
						if (j.getKey() == "valor" && a.getKey() == "Latitud") {
							System.out.println( a.getKey() + " -> " +j.getValue());
							auxiliarLlenadoFlotantes = Integer.parseInt(j.getValue(),16);
							j.setValue(Float.toString(auxiliarLlenadoFlotantes/1000000));
							Latitud2 = Double.parseDouble(j.getValue());
						}
						if (j.getKey() == "valor" && a.getKey() == "Longitud") {
							System.out.println( a.getKey() + " -> " + j.getValue());
							auxiliarLlenadoFlotantes = Integer.parseInt(j.getValue(),16);
							j.setValue(Float.toString(auxiliarLlenadoFlotantes/1000000));
							longitude2 = Double.parseDouble(j.getValue());
						}
						if (j.getKey() == "valor" && a.getKey() == "Direccion") {
							System.out.println( a.getKey() + " -> " + j.getValue());
							auxiliarLlenadoEnetros = Integer.parseInt(j.getValue(),16);
							j.setValue(Integer.toString(auxiliarLlenadoEnetros));
							headingDeg = auxiliarLlenadoEnetros ;
						}
						
						// Fecha y Tiempo
						if (j.getKey() == "valor" && a.getKey() == "Fecha") {
							System.out.println( a.getKey() + " -> " + j.getValue());
							Fecha = j.getValue() ;
						}
						if (j.getKey() == "valor" && a.getKey() == "Tiempo") {
							System.out.println( a.getKey() + " -> " + j.getValue());
							Tiempo = j.getValue(); 
						}
						
						if (j.getKey() == "valor" && a.getKey() == "Altitud") {
							System.out.println( a.getKey() + " -> " + j.getValue());
							auxiliarLlenadoEnetros = Integer.parseInt(j.getValue(),16);
							j.setValue(Integer.toString(auxiliarLlenadoEnetros));
							altitudeM = auxiliarLlenadoEnetros ;
						}
						if (j.getKey() == "valor" && a.getKey() == "Velocidad") {
							System.out.println( a.getKey() + " -> " + j.getValue());
							auxiliarLlenadoEnetros = Integer.parseInt(j.getValue(),16);
							j.setValue(Integer.toString(auxiliarLlenadoEnetros));
							speedKPH2 = auxiliarLlenadoEnetros * 0.1 ; 
						}
						if (j.getKey() == "valor" && a.getKey() == "Bateria") {
							System.out.println( a.getKey() + " -> " + j.getValue());
							TramaExtendida = j.getValue();
							auxiliarLlenadoEnetros = Integer.parseInt(j.getValue().substring(4),16);
							j.setValue(Integer.toString(auxiliarLlenadoEnetros));
							batteryLevel2 = auxiliarLlenadoEnetros; 
						}
						if (j.getKey() == "valor" && a.getKey() == "BateriaVoltage") {
							System.out.println( a.getKey() + " -> " + j.getValue());
							TramaExtendida = TramaExtendida + j.getValue();
							auxiliarLlenadoFlotantes = Integer.parseInt(j.getValue().substring(5),16);
							j.setValue(Float.toString(auxiliarLlenadoFlotantes/100));
							batteryVolts2 = Double.parseDouble(j.getValue());
						}
						if (j.getKey() == "valor" && a.getKey() == "intensidad") {
							System.out.println( a.getKey() + " -> " + j.getValue());
							TramaExtendida = TramaExtendida + j.getValue();
							auxiliarLlenadoEnetros = Integer.parseInt(j.getValue().substring(4),16);
							j.setValue(Integer.toString(auxiliarLlenadoEnetros));
							signalStrength2 = auxiliarLlenadoEnetros;
						}
						if (j.getKey() == "valor" && a.getKey() == "NumeroSatelites") {
							System.out.println( a.getKey() + " -> " + j.getValue());
							TramaExtendida = TramaExtendida + j.getValue();
							auxiliarLlenadoEnetros = Integer.parseInt(j.getValue().substring(4),16);
							j.setValue(Integer.toString(auxiliarLlenadoEnetros));
							satelliteCount2 = auxiliarLlenadoEnetros ; 
						}
						if (j.getKey() == "valor" && a.getKey() == "VersionProtocolo") {
							System.out.println( a.getKey() + " -> " + j.getValue());
							TramaExtendida = TramaExtendida + j.getValue();
							auxiliarLlenadoEnetros = Integer.parseInt(j.getValue().substring(4),16);
							j.setValue(Integer.toString(auxiliarLlenadoEnetros));
							VersionProtocolo = auxiliarLlenadoEnetros ; 
						}
						if (j.getKey() == "valor" && a.getKey() == "Odometro") {
							System.out.println( a.getKey() + " -> " + j.getValue());
							TramaExtendida = TramaExtendida + j.getValue();
							auxiliarLlenadoEnetros = Integer.parseInt(j.getValue().substring(4),16);
							j.setValue(Integer.toString(auxiliarLlenadoEnetros));
							odomKM2 = auxiliarLlenadoEnetros ; 
						}
						if (j.getKey() == "valor" && a.getKey() == "InformacionCelda") {
							System.out.println( a.getKey() + " -> " + j.getValue());
							TramaExtendida = TramaExtendida + j.getValue();
							mobileCountryCode2 = Integer.parseInt(j.getValue().substring(5,8),16);
							mobileNetworkCode2 = Integer.parseInt(j.getValue().substring(8,10),16);
							cellTowerID2 = Integer.parseInt(j.getValue().substring(10,18),16);
							locationAreaCode2 = Integer.parseInt(j.getValue().substring(18,22),16);
							j.setValue((mobileCountryCode2+"_"+mobileNetworkCode2+"_"+cellTowerID2+"_"+locationAreaCode2));
						}
						
						if (j.getKey() == "valor" && a.getKey() == "FenceAlarmID") {
							System.out.println( a.getKey() + " -> " + j.getValue());
							TramaExtendida = TramaExtendida + j.getValue();
							auxiliarLlenadoEnetros = Integer.parseInt(j.getValue().substring(4),16);
							j.setValue(Integer.toString(auxiliarLlenadoEnetros));
							odomKM2 = auxiliarLlenadoEnetros ; 
						}
					}
				}
				
			System.out.println("\n###########################################################");
			System.out.println("TramaExtendida = " +TramaExtendida);
			System.out.println("###########################################################\n");
	
			int numHex2 = Integer.parseInt(numHex, 16);
			String binary = Integer.toBinaryString(numHex2);
			
			String[] strarray = binary.split("");
						

			System.out.println("Latitud2 ->  "+Latitud2);
			System.out.println("longitude2 ->  " + longitude2 );
			System.out.println("altitudeM -> " + altitudeM);
			System.out.println("speedKPH2 -> "+ speedKPH2);
			System.out.println("headingDeg -> " + headingDeg);
			System.out.println("batteryLevel2 -> "+ batteryLevel2);
			System.out.println("batteryVolts2 -> " + batteryVolts2);
			System.out.println("signalStrength2 -> " + signalStrength2);
			System.out.println("satelliteCount2 -> "  + satelliteCount2);
			System.out.println("odomKM2 -> " + odomKM2);
			
			System.out.println("mobileCountryCode2 -> " + mobileCountryCode2);
			System.out.println("mobileNetworkCode2 -> " + mobileNetworkCode2);
			System.out.println("cellTowerID2 -> " + cellTowerID2);
			System.out.println("locationAreaCode2 -> " + locationAreaCode2);
			System.out.println("gpsFixStatus -> "+ gpsFixStatus);
		
		return null; 
	}
			
	public String [] BusquedaCaracteres(String Trama, String Separador) {
		String [] ArrayCaracteres = new String[2] ;
		String ConvertTramaVersionProtocolo = Trama ; 
		ConvertTramaVersionProtocolo = ConvertTramaVersionProtocolo.replace(Separador, "-");
		String [] TramaVersionProtocolo = ConvertTramaVersionProtocolo.split("-");
		String Valor = TramaVersionProtocolo[0];
		int TramaVersionProtocoloLength = TramaVersionProtocolo[0].length();
		int Tramalength = Trama.length();
		Trama = Trama.substring(TramaVersionProtocoloLength , Tramalength);
		ArrayCaracteres[0]= Trama ;
		ArrayCaracteres[1] = Valor;
		return ArrayCaracteres; 	
	}
} 






package Inicio;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Jointech701 {
	
	public byte[] setData(String trama701) {
		System.out.println("llego la trama");
		System.out.println(trama701);
		String s = trama701;
		s = s.replaceAll("3D15","28");
		s = s.replaceAll("3D14","29");
		s = s.replaceAll("3D11","2C");
		s = s.replaceAll("3D00","3D");
		System.out.println(" \n TRAMA SIN ESCAPE -> \n ------------------ \n" + s );
		System.out.println(" \n Length de la trama = " + s.length() +"\n");
		String Mensaje = "";
		String Trama = "";
		String xor = "";
		byte[] respuesta = null;
		if(s.length() >= 100 && s.startsWith("24") ) {
			String CeroDoscientos = s.substring(2,6);
			String Device = s.substring(10,22);
			String DataSerial = s.substring(22,26);
			//String xor =  xor_cal(s);
			if(CeroDoscientos.equals("0200")){
				Trama = "80010005" + Device + "0001" + DataSerial + "020000";
				xor = xor_cal(Trama);
				Mensaje = "7E" + Trama + xor + "7E";
			}
			return parseInsertRecord_JOINTECH(s);
			//Parseo trama clave dinamica ** pendiente insertar la clave en la base de datos
		}else if(s.startsWith("28") && s.endsWith("29")) {
			String TramaAscci =hexToAscii(s);
			String TramaAscciMod = TramaAscci.replace("(","");
			TramaAscciMod = TramaAscciMod.replace(")","");
			String [] TramaArray = TramaAscciMod.split(",");
			System.out.println("\n Mensaje " + TramaAscciMod + "\n");
			
		}else if(s.startsWith("7E") && s.endsWith("7E")) {
			String CeroDos = s.substring(2,6);
			String CeroCero = s.substring(6,10);
			String Device = s.substring(10,22);
			String CeroUno = s.substring(22,26);
			//String xor =  xor_cal(s);
			if(CeroDos.equals("0002") && CeroCero.equals("0000")){
				Trama = "80010005" + Device + "0001" + CeroUno + CeroDos + "00";
				xor = xor_cal(Trama);
				Mensaje = "7E" + Trama + xor + "7E";
				System.out.println(" \n La trama es Hearbeat es " + s + " Mensaje " + Mensaje + "\n");
				//return Mensaje.getBytes(StandardCharsets.UTF_8);
			}
		}
		return respuesta;	
	}
	
	private String xor_cal_val(String valor) {
		int AnchoValor = valor.length();
		int max = AnchoValor - 6;
		valor = valor.substring(2,AnchoValor);
		valor = valor.substring(0,max);
		int i = 2;
		int x = 4;
		String a = valor.substring(0,2);
		String b = "";
		while (i<=max-2){
			b = valor.substring(i,x);
			i=i+2;
			x=x+2;
			int n1 = Integer.parseInt(a,16);
			int n2 = Integer.parseInt(b,16);
			int n3 = n1 ^ n2;
			a = String.format("%02x", n3);
		}
		return a;
	}
	
	private String xor_cal(String valor) {
		int i = 2;
		int x = 4;
		String a = valor.substring(0,2);
		String b = "";
		while (i<=valor.length()-2){
			b = valor.substring(i,x);
			//System.out.println(" \n El valor de b " + b);
			i=i+2;
			x=x+2;
			int n1 = Integer.parseInt(a,16);
			int n2 = Integer.parseInt(b,16);
			int n3 = n1 ^ n2;
			a = String.format("%02x", n3);
		}
		return a.toUpperCase();
	}
	
	private static String hexToAscii(String hexStr) {
	    StringBuilder output = new StringBuilder("");
	    
	    for (int i = 0; i < hexStr.length(); i += 2) {
	        String str = hexStr.substring(i, i + 2);
	        output.append((char) Integer.parseInt(str, 16));
	    }
	    
	    return output.toString();
	}
	
	private int stringtobindec(String valor) {
		String reversedString = new StringBuilder(valor).reverse().toString();
		int n = reversedString.length();
		int v = 1;
		int res = 0;
		char[] a = reversedString.toCharArray();
		for (int i = 0; i <= n - 1; i++)
    	{
			int val =  Integer.valueOf(a[i])-48;
			res = res + val*v;
			v=v*2;
    	}
		return res;
	}
	
	private String hexToBin(String hex){
	    String bin = "";
	    String binFragment = "";
	    int iHex;
	    hex = hex.trim();
	    hex = hex.replaceFirst("0x", "");

	    for(int i = 0; i < hex.length(); i++){
	        iHex = Integer.parseInt(""+hex.charAt(i),16);
	        binFragment = Integer.toBinaryString(iHex);

	        while(binFragment.length() < 4){
	            binFragment = "0" + binFragment;
	        }
	        bin += binFragment;
	    }
	    return bin;
	}

	
	private byte[] parseInsertRecord_JOINTECH(String s){
		System.out.println("llego a funcion parseInsertRecord_JOINTECH");
		if (s == null) {
			System.out.println("String is null \n");
			return null;
		}
		int Datalength = 01;
		double  HDOP   = 0.0;
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
		
		LinkedHashMap<String, LinkedHashMap<String, String>> indicador = new LinkedHashMap<String, LinkedHashMap<String, String>>();
		indicador.put("Encabezado", generateHashMapValues("2", ""));
		indicador.put("NumeroIdentificacion",  generateHashMapValues("10", ""));
		indicador.put("VersionProtocolo",  generateHashMapValues("2", ""));
		indicador.put("TipoDispositivo",  generateHashMapValues("1", ""));
		indicador.put("TipoDato",  generateHashMapValues("1", ""));
		indicador.put("DataLength",  generateHashMapValues("4", ""));
		indicador.put("FechaCompleta",  generateHashMapValues("12", ""));
		indicador.put("Latitud",  generateHashMapValues("8", ""));
		indicador.put("Longitud",  generateHashMapValues("9", ""));
		indicador.put("IndicadorDireccion",  generateHashMapValues("1", ""));
		indicador.put("Velocidad",  generateHashMapValues("2", ""));
		indicador.put("Direccion",  generateHashMapValues("2", ""));
		indicador.put("Odometro",  generateHashMapValues("8", ""));
		indicador.put("NumeroSatelites",  generateHashMapValues("2", ""));
		indicador.put("BindVehicleId",  generateHashMapValues("8", ""));
		indicador.put("DeviceStatus",  generateHashMapValues("4", ""));
		indicador.put("Bateria",  generateHashMapValues("2", ""));
		indicador.put("CellIdPos",  generateHashMapValues("8", ""));
		
		indicador.put("intensidad",  generateHashMapValues("2", ""));
		indicador.put("FenceAlarmID",  generateHashMapValues("2", ""));
		indicador.put("ExpandDeviceStatus",  generateHashMapValues("2", ""));
		indicador.put("Reserved1",  generateHashMapValues("4", ""));
		indicador.put("Imei",  generateHashMapValues("16", ""));
		indicador.put("CellId",  generateHashMapValues("4", ""));
		indicador.put("MCC",  generateHashMapValues("4", ""));
		indicador.put("MNC",  generateHashMapValues("2", ""));
		indicador.put("DataSerialNumber",  generateHashMapValues("2", ""));
		
	
	
		System.out.println(indicador);
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
					//System.out.println(" k es " + k + " acumulador " + acumulador + "El valor de J es " + tramaPorcionada);
					j.setValue(tramaPorcionada);
				}
			}
		}
		
		System.out.println("Caracteres viejos ------------------");
	
		double  Latitud 	  				= 0.0;
		double  longitude2					= 0.0;
		double  altitudeM						= 0.0; 
		double 	headingDeg					= 0.0; 
		double  speedKPH2    				= 0.0;
		double  odomKM2      				= 0.0;
		int     numSats        			= 0;
		String     Fc       			= "";
		String 	Estado 	="";	
		double  batteryLevel				= 0.0;
		double  batteryVolts2 			= 0.0;
		double  signalStrength2			= 0.0;
		int  	satelliteCount2				= 0;
		int		mobileCountryCode2 		= 0;
		int 	mobileNetworkCode2 		= 0;
		int  	cellTowerID2   				= 0;
		int locationAreaCode2			= 0;
		int VersionProtocolo 				= 0;
		int statusCode 					= 62000;
		String VersionExtendida = "";
		String TipoDispositivo = "";
		int DataSerialNumber = 0;
		String IndicadorDireccion = "";
		int Direccion = 0;
		String BindVehicleId = "";
		int TipoDato = 0;
		
		String CellId= "";
		String CellIdPos = "";
		String CellID1 = "";
		String Lac = "";
		int MCC = 0;
		int MNC = 0 ;
		System.out.println(" ------ CONVERTIR TRAMA Y GRUARDAR DE NUEVO EN INDICADOR LINKEDHASMAP  -------------------------");
		String mobileID 	= "" ;
		long    fixtime   = 0 ;
		for(Map.Entry<String, LinkedHashMap<String, String>> a: indicador.entrySet()) {
			for(Map.Entry<String, String> j:  a.getValue().entrySet()) {
				if (j.getKey() == "valor" && a.getKey() == "NumeroIdentificacion") {
					System.out.println( a.getKey() + " -> " +j.getValue());
					mobileID = j.getValue();
				}
				if (j.getKey() == "valor" && a.getKey() == "TipoDispositivo") {
					System.out.println( a.getKey() + " -> " +j.getValue());
					TipoDispositivo = j.getValue();
				}
				if (j.getKey() == "valor" && a.getKey() == "TipoDato") {
					System.out.println( a.getKey() + " -> " + j.getValue());
					TipoDato = Integer.parseInt(j.getValue());
				}
				if (j.getKey() == "valor" && a.getKey() == "DeviceStatus") {
					System.out.println( a.getKey() + " -> " + j.getValue());
					Estado = j.getValue();
				}
				if (j.getKey() == "valor" && a.getKey() == "Latitud") {
					System.out.println( a.getKey() + " -> " +j.getValue());
					int numero1 = Integer.parseInt(j.getValue().substring(0,2));
					auxiliarLlenadoFlotantes = Integer.parseInt(j.getValue().substring(2,8));
					Latitud = auxiliarLlenadoFlotantes/600000 + numero1;
				}
				if (j.getKey() == "valor" && a.getKey() == "Longitud") {
					System.out.println( a.getKey() + " -> " + j.getValue());
					int numero1 = Integer.parseInt(j.getValue().substring(0,3));
					auxiliarLlenadoFlotantes = Integer.parseInt(j.getValue().substring(3,9));
					longitude2 = auxiliarLlenadoFlotantes/600000 + numero1;
				}
				if (j.getKey() == "valor" && a.getKey() == "IndicadorDireccion") {
					System.out.println( a.getKey() + " -> " + j.getValue());
					IndicadorDireccion = j.getValue();
				}
				if (j.getKey() == "valor" && a.getKey() == "FechaCompleta") {
					System.out.println( a.getKey() + " -> " + j.getValue());
					Fc = j.getValue();
				}
				if (j.getKey() == "valor" && a.getKey() == "Velocidad") {
					System.out.println( a.getKey() + " -> " + j.getValue());
					auxiliarLlenadoEnetros = Integer.parseInt(j.getValue(),16);
					j.setValue(Integer.toString(auxiliarLlenadoEnetros));
					speedKPH2 = auxiliarLlenadoEnetros * 1.85 ; 
				}
				if (j.getKey() == "valor" && a.getKey() == "Direccion") {
					System.out.println( a.getKey() + " -> " + j.getValue());
					auxiliarLlenadoEnetros = Integer.parseInt(j.getValue(),16);
					j.setValue(Integer.toString(auxiliarLlenadoEnetros));
					Direccion = auxiliarLlenadoEnetros * 2 ; 
				}
				if (j.getKey() == "valor" && a.getKey() == "Odometro") {
					System.out.println( a.getKey() + " -> " + j.getValue());
					auxiliarLlenadoEnetros = Integer.parseInt(j.getValue(),16);
					j.setValue(Integer.toString(auxiliarLlenadoEnetros));
					odomKM2 = auxiliarLlenadoEnetros ; 
				}
				if (j.getKey() == "valor" && a.getKey() == "NumeroSatelites") {
					System.out.println( a.getKey() + " -> " + j.getValue());
					auxiliarLlenadoEnetros = Integer.parseInt(j.getValue(),16);
					j.setValue(Integer.toString(auxiliarLlenadoEnetros));
					satelliteCount2 = auxiliarLlenadoEnetros ; 
				}
				if (j.getKey() == "valor" && a.getKey() == "BindVehicleId") {
					System.out.println( a.getKey() + " -> " + j.getValue());
					BindVehicleId = j.getValue();
				}
				if (j.getKey() == "valor" && a.getKey() == "Bateria") {
					System.out.println( a.getKey() + " -> " + j.getValue());
					VersionExtendida = j.getValue();
					auxiliarLlenadoEnetros = Integer.parseInt(j.getValue(),16);
					j.setValue(Integer.toString(auxiliarLlenadoEnetros));
					batteryLevel = auxiliarLlenadoEnetros; 
				}
				
				//---------------------------------------------------------------------
				if (j.getKey() == "valor" && a.getKey() == "CellId") {
					System.out.println( a.getKey() + " -> " + j.getValue());
					CellId = j.getValue();
				}
				
				if (j.getKey() == "valor" && a.getKey() == "CellIdPos") {
					System.out.println( a.getKey() + " -> " + j.getValue());
					CellIdPos = j.getValue();
					CellID1 = CellIdPos.substring(0,4);
					Lac = CellIdPos.substring(4,8);
					System.out.println("----------------------------------");
					System.out.println("CellID1 ===== " + CellID1);
					System.out.println("Lac ======= " + Lac);
				}
				
				if (j.getKey() == "valor" && a.getKey() == "MCC") {
					System.out.println( a.getKey() + " -> " + j.getValue());
					auxiliarLlenadoEnetros = Integer.parseInt(j.getValue(),16);
					j.setValue(Integer.toString(auxiliarLlenadoEnetros));
					MCC = auxiliarLlenadoEnetros ; 
				}
				
				if (j.getKey() == "valor" && a.getKey() == "MNC") {
					System.out.println( a.getKey() + " -> " + j.getValue());
					auxiliarLlenadoEnetros = Integer.parseInt(j.getValue(),16);
					j.setValue(Integer.toString(auxiliarLlenadoEnetros));
					MNC = auxiliarLlenadoEnetros ; 
				}
				
				if (j.getKey() == "valor" && a.getKey() == "DataSerialNumber") {
					System.out.println( a.getKey() + " -> " + j.getValue());
					auxiliarLlenadoEnetros = Integer.parseInt(j.getValue(),16);
					j.setValue(Integer.toString(auxiliarLlenadoEnetros));
					DataSerialNumber = auxiliarLlenadoEnetros ; 
				}
				
				

			}
		}
		
		
		
		//Bits de posicion
		String bDireccion = hexToBin(IndicadorDireccion);
		int menosuno = -1;
		int bGpsLongitud = Integer.parseInt(bDireccion.substring(1,2));
		int bGpsLatitud = Integer.parseInt(bDireccion.substring(2,3));
		int bGpsposicionado = Integer.parseInt(bDireccion.substring(3,4));
		if (bGpsLatitud == 0){
			Latitud = Latitud*menosuno;
		}
		if (bGpsLongitud == 0){
			longitude2 = longitude2*menosuno;
		}
		HDOP = bGpsposicionado;
		//Bytes de estado y alarma
		String Bit1_estado = Estado.substring(0,2);
		String Bit2_estado = Estado.substring(2,4);

		String byte1 = hexToBin(Bit1_estado);
		String byte2 = hexToBin(Bit2_estado);

		//bits de Alarma
		int bAlmEntGeocerca = Integer.parseInt(byte2.substring(6,7));
		int bAlmSalGeocerca = Integer.parseInt(byte2.substring(5,6));
		int bAlmCorteGuaya = Integer.parseInt(byte2.substring(4,5));
		int bAlmVibracion = Integer.parseInt(byte2.substring(3,4));

		int bAlmBloqLargaDur = Integer.parseInt(byte1.substring(7,8));
		int bAlmClInc5Veces = Integer.parseInt(byte1.substring(6,7));
		int bAlmTjRfIncorrecta = Integer.parseInt(byte1.substring(5,6));
		int bAlmBateBaja = Integer.parseInt(byte1.substring(4,5));
		int bAlmTapaAbierta = Integer.parseInt(byte1.substring(3,4));
		int bAlmMotorAtascado = Integer.parseInt(byte1.substring(1,2));

		String validacion2 = Integer.toString(bAlmEntGeocerca) + Integer.toString(bAlmSalGeocerca) + Integer.toString(bAlmCorteGuaya) + Integer.toString(bAlmVibracion) +
		Integer.toString(bAlmBloqLargaDur) + Integer.toString(bAlmClInc5Veces) + Integer.toString(bAlmTjRfIncorrecta) + Integer.toString(bAlmBateBaja) + 
		Integer.toString(bAlmTapaAbierta) + Integer.toString(bAlmMotorAtascado);
		int resultadec2 = stringtobindec(validacion2);		
		System.out.println("\n validacion2 es " + validacion2 + " Valor en Decimal2 " + resultadec2 );

		HashMap<Integer,Integer> StatusbitsHash2 = new HashMap<Integer,Integer>();  
			StatusbitsHash2.put(512,45126);
			StatusbitsHash2.put(256,45127);
			StatusbitsHash2.put(128,45106);
			StatusbitsHash2.put(64,45128);
			StatusbitsHash2.put(32,45105);
			StatusbitsHash2.put(16,45129);
			StatusbitsHash2.put(8,45130);
			StatusbitsHash2.put(4,45131);
			StatusbitsHash2.put(2,45132);
			StatusbitsHash2.put(1,45108);
		for (Map.Entry<Integer, Integer> entry : StatusbitsHash2.entrySet()) {
			if ( entry.getKey() == resultadec2) {
				STATUS_CODE = entry.getValue();
			}
		}
		
		


		System.out.println("\n El bit de estado uno es " + Bit1_estado + " byte " + byte1 +" el dos es " +  Bit2_estado + " byte2 " + byte2 + "\n");
		//TRAMA EXTENDIDA
		System.out.println("\n ################################################### \n" + VersionExtendida + "\n ###################################################  \n" );
		//Convertir a fecha hora
		String FechaCompleta = "20" + Fc.substring(4,6) + "-" + Fc.substring(2,4) + "-" + Fc.substring(0,2) + " " 
		+ Fc.substring(6,8) + ":" + Fc.substring(8,10) + ":" + Fc.substring(10,12);
		//Convertir a Timestamp
		//Timestamp timestamp2 = Timestamp.valueOf(FechaCompleta);
		//Convertir a Unixtime
		//Long gpsTime = new Long(timestamp2.getTime()/1000);
		//gpsTime = gpsTime - 18000;
		String rawData = s;
		System.out.println("rawdata -> "+ rawData);
		System.out.println("Fechacompleta -> " + FechaCompleta + "Fecha -> " + Fc);
		
		/*if( STATUS_CODE > 0 ){
			statusCode = STATUS_CODE;
		}else if( STATUS_CODE2 > 0 ){
			statusCode = STATUS_CODE2;
		}*/

		//int bTapa =bEstadoTapa;
		int bTapa = 0; 
		speedKPH2 = speedKPH2/10;
		
		int LacHex = Integer.parseInt(Lac,16);
		Lac = Integer.toString(LacHex);
		
		String HexCellId = CellId+CellID1;
		int CellIdHex = Integer.parseInt(HexCellId,16);
		
		CellId = Integer.toString(CellIdHex);
		
		System.out.println("CellIdHex =  " + CellIdHex); 
		
		System.out.println("statusCode - > "+ statusCode);
		System.out.println("fixtime -> " + fixtime);
		System.out.println("VARIABLES ----------------------------- ");

		System.out.println("_____________________");
		System.out.println("Estado ->  " + Estado);
		System.out.println("Latitud ->  "+Latitud);
		System.out.println("longitude2 ->  " + longitude2 );
		System.out.println("altitudeM -> " + altitudeM);
		System.out.println("speedKPH2 -> "+ speedKPH2);
		System.out.println("headingDeg -> " + headingDeg);
		System.out.println("batteryLevel -> "+ batteryLevel);
		System.out.println("batteryVolts2 -> " + batteryVolts2);
		System.out.println("signalStrength2 -> " + signalStrength2);
		System.out.println("satelliteCount2 -> "  + satelliteCount2);
		System.out.println("VersionProtocolo -> " + VersionProtocolo );
		System.out.println("odomKM2 -> " + odomKM2 );
		
		System.out.println("mobileCountryCode2 -> " + mobileCountryCode2);
		System.out.println("mobileNetworkCode2 -> " + mobileNetworkCode2);
		System.out.println("cellTowerID2 -> " + cellTowerID2);
		System.out.println("locationAreaCode2 -> " + locationAreaCode2);
		System.out.println("CellIdPos -> " + CellIdPos);
		System.out.println("CellId -> " + CellId);
		System.out.println("Lac -> " + Lac);
		System.out.println("MCC -> " + MCC);
		System.out.println("MNC -> " + MNC);
		System.out.println("DataSerialNumber -> " + DataSerialNumber);
		System.out.println("\n");
		
		
		
		
		return null;	
		
	}

	private LinkedHashMap<String, String> generateHashMapValues(String caracteres, String valor) {
		LinkedHashMap<String, String> valores = new LinkedHashMap<String, String>();
		valores.put("caracteres", caracteres);
		valores.put("valor", valor);
		return valores;
	}
	
}


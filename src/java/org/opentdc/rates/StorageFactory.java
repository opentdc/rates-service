package org.opentdc.rates;

import javax.servlet.ServletContext;

import org.opentdc.exception.NotImplementedException;
import org.opentdc.util.ServiceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StorageFactory {
	private static Logger logger = LoggerFactory
			.getLogger("org.opentdc.rates.StorageFactory");

	public static StorageProvider getStorageProvider(ServletContext context) {
		ServiceType _storageType = ServiceType.getStorageType(context
				.getInitParameter("storage.type"));
		logger.info("> getStorageProvider(): using storageType " + _storageType);

		StorageProvider _sp = null;
		switch (_storageType) {
		case TRANSIENT:
			_sp = new FileImpl(context, false);
			break;
		case FILE:
			_sp = new FileImpl(context, true);
			break;
		case OPENCRX:
			throw new NotImplementedException(
					"Rates service is not yet implemented with OpenCRX storage");
		case MONGODB:
			throw new NotImplementedException(
					"Rates services is not yet implemented with MongoDB storage");
		default:
			throw new NotImplementedException(
					"Rates services has unknown storage type <" + _storageType
							+ "> configured.");
		}
		return _sp;
	}
}

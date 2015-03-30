package org.opentdc.rates;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.opentdc.exception.DuplicateException;
import org.opentdc.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class FileImpl implements StorageProvider {
	private static final String SEED_FN = "/seed.json";
	private static final String DATA_FN = "/data.json";
	private static File dataF = null;
	private static File seedF = null;
	private static Map<String, RatesModel> data = null;

	// instance variables
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private boolean isPersistent = true;

	public FileImpl(ServletContext context, boolean makePersistent) {
		logger.info("> FileImpl()");

		isPersistent = makePersistent;
		if (dataF == null) {
			dataF = new File(context.getRealPath(DATA_FN));
		}
		if (seedF == null) {
			seedF = new File(context.getRealPath(SEED_FN));
		}
		if (data == null) {
			data = new HashMap<String, RatesModel>();
		}
		if (data.size() == 0) {
			importJson();
		}

		logger.info("FileImpl() initialized");
	}

	@Override
	public ArrayList<RatesModel> list() {
		logger.info("list() -> " + count() + " values");
		return new ArrayList<RatesModel>(data.values());
	}

	@Override
	public RatesModel create(RatesModel rate) throws DuplicateException {
		logger.info("create(" + rate + ")");
		String _id = rate.getId();
		if (_id != null && _id != "" && data.get(rate.getId()) != null) {
			// object with same ID exists already
			throw new DuplicateException();
		}
		RatesModel _rate = new RatesModel(rate.getTitle(), rate.getRate(),
				rate.getDescription());
		data.put(_rate.getId(), _rate);
		if (isPersistent) {
			exportJson(dataF);
		}
		return _rate;
	}

	@Override
	public RatesModel read(String id) throws NotFoundException {
		RatesModel _rate = data.get(id);
		if (_rate == null) {
			throw new NotFoundException("no rate with ID <" + id
					+ "> was found.");
		}
		logger.info("read(" + id + ") -> " + _rate);
		return _rate;
	}

	@Override
	public RatesModel update(RatesModel rate) throws NotFoundException {
		// it does not matter whether an object with the same ID already
		// exists. It is either replaced or created.
		data.put(rate.getId(), rate);
		logger.info("update(" + rate + ")");
		if (isPersistent) {
			exportJson(dataF);
		}
		return rate;
	}

	@Override
	public void delete(String id) throws NotFoundException {
		RatesModel _rate = data.get(id);
		;
		if (_rate == null) {
			throw new NotFoundException("delete(" + id
					+ "): no such rate was found.");
		}
		data.remove(id);
		logger.info("delete(" + id + ")");
		if (isPersistent) {
			exportJson(dataF);
		}
	}

	@Override
	public int count() {
		int _count = -1;
		if (data != null) {
			_count = data.values().size();
		}
		logger.info("count() = " + _count);
		return _count;
	}

	void importJson() {
		ArrayList<RatesModel> _rates = null;

		// read the data file
		// either read persistent data from DATA_FN
		// or seed data from SEED_DATA_FN if no persistent data exists
		if (dataF.exists()) {
			logger.info("persistent data in file " + dataF.getName()
					+ " exists.");
			_rates = importJson(dataF);
		} else { // seeding the data
			logger.info("persistent data in file " + dataF.getName()
					+ " is missing -> seeding from " + seedF.getName());
			_rates = importJson(seedF);
		}
		// load the data into the local transient storage
		for (RatesModel _rate : _rates) {
			data.put(_rate.getId(), _rate);
		}
		logger.info("added " + _rates.size() + " rates to data");

		if (isPersistent) {
			// create the persistent data if it did not exist
			if (!dataF.exists()) {
				try {
					dataF.createNewFile();
				} catch (IOException e) {
					logger.error("importJson(): IO exception when creating file "
							+ dataF.getName());
					e.printStackTrace();
				}
				exportJson(dataF);
			}
		}
		logger.info("importJson(): imported " + _rates.size() + " rate objects");
	}

	/******************************** utility methods *****************************************/
	private ArrayList<RatesModel> importJson(File f) throws NotFoundException {
		logger.info("importJson(" + f.getName() + "): importing RatesData");
		if (!f.exists()) {
			logger.error("importJson(" + f.getName()
					+ "): file does not exist.");
			throw new NotFoundException("File " + f.getName()
					+ " does not exist.");
		}
		if (!f.canRead()) {
			logger.error("importJson(" + f.getName()
					+ "): file is not readable");
			throw new NotFoundException("File " + f.getName()
					+ " is not readable.");
		}
		logger.info("importJson(" + f.getName() + "): can read the file.");

		Reader _reader = null;
		ArrayList<RatesModel> _rates = null;
		try {
			_reader = new InputStreamReader(new FileInputStream(f));
			Gson _gson = new GsonBuilder().create();

			Type _collectionType = new TypeToken<ArrayList<RatesModel>>() {
			}.getType();
			_rates = _gson.fromJson(_reader, _collectionType);
			logger.info("importJson(" + f.getName() + "): json data converted");
		} catch (FileNotFoundException e1) {
			logger.error("importJson(" + f.getName()
					+ "): file does not exist (2).");
			e1.printStackTrace();
		} finally {
			try {
				if (_reader != null) {
					_reader.close();
				}
			} catch (IOException e) {
				logger.error("importJson(" + f.getName()
						+ "): IOException when closing the reader.");
				e.printStackTrace();
			}
		}
		logger.info("importJson(" + f.getName() + "): " + _rates.size()
				+ " rates imported.");
		return _rates;
	}

	private void exportJson(File f) {
		logger.info("exportJson(" + f.getName() + "): exporting rates");
		Writer _writer = null;
		try {
			_writer = new OutputStreamWriter(new FileOutputStream(f));
			Gson _gson = new GsonBuilder().create();
			_gson.toJson(data.values(), _writer);
		} catch (FileNotFoundException e) {
			logger.error("exportJson(" + f.getName() + "): file not found.");
			e.printStackTrace();
		} finally {
			if (_writer != null) {
				try {
					_writer.close();
				} catch (IOException e) {
					logger.error("exportJson(" + f.getName()
							+ "): IOException when closing the reader.");
					e.printStackTrace();
				}
			}
		}
	}

}

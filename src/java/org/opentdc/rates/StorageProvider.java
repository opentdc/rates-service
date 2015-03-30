package org.opentdc.rates;

import java.util.ArrayList;

import javax.ws.rs.PathParam;

import org.opentdc.exception.DuplicateException;
import org.opentdc.exception.NotFoundException;

public interface StorageProvider {
	public ArrayList<RatesModel> list();

	public RatesModel create(RatesModel rate) throws DuplicateException;

	public RatesModel read(@PathParam("id") String id) throws NotFoundException;

	public RatesModel update(RatesModel rate) throws NotFoundException;

	public void delete(@PathParam("id") String id) throws NotFoundException;

	public int count();

}

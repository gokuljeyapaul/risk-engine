package com.heimdall.risk.store;

import com.heimdall.risk.model.Model;

/**
 * Interface for all data stores
 *
 */
public interface DataStore {

	/**
	 * Save or update the current model
	 *
	 * @param model
	 * @return Integer
	 */
	public Integer saveOrUpdate(Model model);

	/**
	 * Read a model
	 *
	 * @param id
	 * @return model
	 */
	public <T extends Model> T read(Integer id);

	/**
	 * Delete a model from datastore
	 *
	 * @param id
	 * @return
	 */
	public boolean delete(Integer id);

}

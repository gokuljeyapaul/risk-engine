package com.heimdall.risk.store;

import com.heimdall.risk.model.Model;

/**
 * Interface for all data stores
 *
 */
public interface DataStore {

	public Integer saveOrUpdate(Model model);

	public <T extends Model> T read(Integer id);

	public boolean delete(Integer id);

}

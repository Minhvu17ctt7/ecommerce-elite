
import React, { useState } from 'react'
import { useParams } from 'react-router-dom';
import CardProduct from '../../CardProduct';
import Pagination from '../../Pagination';

const Product = ({ productPagination, handleSearchCallback }) => {
    const { page } = useParams();
    return (

        <div className="col-lg-9 col-md-12">
            <div className="row pb-3">
                <div className="col-12 pb-1">
                    <div className="d-flex align-items-center justify-content-between mb-4">
                        <form action>
                            <div className="input-group">
                                <input type="text" className="form-control" onChange={(e) => handleSearchCallback(e)} placeholder="Search by name" />
                                <div className="input-group-append">
                                    <span className="input-group-text bg-transparent text-primary">
                                        <i className="fa fa-search" />
                                    </span>
                                </div>
                            </div>
                        </form>
                        <div className="dropdown ml-4">
                            <button className="btn border dropdown-toggle" type="button" id="triggerId" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                Sort by
                            </button>
                            <div className="dropdown-menu dropdown-menu-right" aria-labelledby="triggerId">
                                <a className="dropdown-item">A-Z</a>
                                <a className="dropdown-item">Z-A</a>
                            </div>
                        </div>
                    </div>
                </div>
                {productPagination?.products.map(product => (<CardProduct key={product.id} product={product} />))}
                <div className="col-12 pb-1">
                    <nav aria-label="Page navigation">
                        {productPagination?.totalPage > 1 && (<Pagination url={"/shop/"}
                            totalPage={parseInt(productPagination?.totalPage)}
                            currentPage={parseInt(page)} />)}
                    </nav>
                </div>
            </div>
        </div>
    );
}

export default Product
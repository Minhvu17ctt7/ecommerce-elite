
import React, { useState } from 'react'
import { Navigate, useNavigate, useParams, useSearchParams } from 'react-router-dom';
import CardProduct from '../../CardProduct';
import Pagination from '../../Pagination2';

const Product = ({ productPagination }) => {
    const [searchParams] = useSearchParams();
    const page = searchParams.get("page") || 1;
    const categoryId = searchParams.get("categoryId");
    const search = searchParams.get("search")
    const navigate = useNavigate();

    const handleNextPage = (nextPage) => {
        let url = `/shop?page=${nextPage}`;
        if (categoryId) {
            url += `&categoryId=${categoryId}`;
        }
        if (search) {
            url += `&search=${search}`
        }
        navigate(url);
    }

    const handleSearch = (e) => {
        let url = `/shop?page=${page}`;
        if (categoryId) {
            url += `&categoryId=${categoryId}`;
        }
        url += `&search=${e.target.value}`
        navigate(url);
    }

    return (

        <div className="col-lg-9 col-md-12">
            <div className="row pb-3">
                <div className="col-12 pb-1">
                    <div className="d-flex align-items-center justify-content-between mb-4">
                        <form action>
                            <div className="input-group">
                                <input type="text" className="form-control" onChange={(e) => handleSearch(e)} placeholder="Search by name" />
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
                            currentPage={parseInt(page)}
                            handleNextPage={handleNextPage}
                        />)}
                    </nav>
                </div>
            </div>
        </div>
    );
}

export default Product
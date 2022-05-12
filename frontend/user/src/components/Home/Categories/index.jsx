import React from 'react'
import { Link, useSearchParams } from 'react-router-dom';

const Categories = ({ categories }) => {
    return (

        <div className="container-fluid pt-5">
            <div className="row px-xl-5 pb-3">
                {
                    categories.map((category) => (
                        <div className="col-lg-4 col-md-6 pb-1" key={category.id}>
                            <div className="cat-item d-flex flex-column border mb-4" style={{ padding: '30px' }}>
                                <p className="text-right">{category.quantity} Products</p>
                                <Link to={`/shop?categoryId=${category.id}`} className="cat-img position-relative overflow-hidden mb-3">
                                    <img className="img-fluid" src={category.image} alt="" />
                                </Link>
                                <h5 className="font-weight-semi-bold m-0">{category.name}</h5>
                            </div>
                        </div>
                    ))
                }

            </div>
        </div>
    );
}

export default Categories
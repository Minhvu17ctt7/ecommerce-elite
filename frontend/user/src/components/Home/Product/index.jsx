import React from 'react'
import CardProduct from '../../CardProduct';

const Product = ({ productPagination }) => {

    return (

        <div className="container-fluid pt-5">
            <div className="text-center mb-4">
                <h2 className="section-title px-5"><span className="px-2">Trandy Products</span></h2>
            </div>
            <div className="row px-xl-5 pb-3">
                {
                    productPagination && productPagination?.products.map(product => <CardProduct key={product.id} product={product} />)
                }
            </div>
        </div>
    );
}

export default Product
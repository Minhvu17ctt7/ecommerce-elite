import { useSnackbar } from 'notistack'
import React from 'react'
import { useDispatch } from 'react-redux'
import { Link } from 'react-router-dom'
import { addToCartAction } from '../../redux/actions/cartActions'
import { formatCurrency } from '../../util/util'

const CardProduct = ({ product }) => {

    const dispatch = useDispatch();
    const { enqueueSnackbar } = useSnackbar();

    const addToCart = () => {
        const productAddToCart = {
            "id": product.id,
            "quantity": 1,
            "productId": product.id,
            "nameProduct": product.name,
            "priceProduct": product.price,
            "mainImage": product.mainImage
        }
        dispatch(addToCartAction(productAddToCart));
        enqueueSnackbar("Add to cart success", { variant: "success", autoHideDuration: 3000 });
    }

    return (
        <div className="col-lg-4 col-md-6 col-sm-12 pb-1">
            <div className="card product-item border-0 mb-4 h-100">
                <div className="card-header product-img position-relative overflow-hidden bg-transparent border p-0">
                    <img className="img-fluid w-100" src={product.mainImage} alt="" />
                </div>
                <div className="card-body border-left border-right text-center p-0 pt-4 pb-3">
                    <h6 className="text-truncate mb-3">{product.name}</h6>
                    <div className="d-flex justify-content-center">
                        <h6>{formatCurrency(product.price)}</h6><h6 className="text-muted ml-2"><del>{formatCurrency(product.price)}</del></h6>
                    </div>
                </div>
                <div className="card-footer d-flex justify-content-between bg-light border">
                    <Link to={`/products/${product.id}?pageReview=1`} className="btn btn-sm text-dark p-0"><i className="fas fa-eye text-primary mr-1" />View Detail</Link>
                    <button className="btn btn-sm text-dark p-0" onClick={() => addToCart()}  ><i className="fas fa-shopping-cart text-primary mr-1" />Add To Cart</button>
                </div>
            </div>
        </div>
    )
}

export default CardProduct
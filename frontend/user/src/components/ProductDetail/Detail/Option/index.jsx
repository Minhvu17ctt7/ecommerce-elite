import React from 'react'
import { Rating } from 'react-simple-star-rating';
import { formatCurrency } from '../../../../uitl/util';

const Option = ({ product }) => {

    return (

        <div className="col-lg-7 pb-5">
            <h3 className="font-weight-semi-bold">{product?.name}</h3>
            <div className="d-flex mb-3">
                <div className="text-primary mr-2">
                    <Rating readonly ratingValue={product?.averageRating * 20} />
                </div>
                <small className="pt-1">{product?.reviews.length} review</small>
            </div>
            <h4 className="font-weight-semi-bold mb-4">{product && formatCurrency(product?.price)}</h4>
            <p className="mb-4">{product?.shortDescription}</p>
            <div className="d-flex mb-3">
                <p className="text-dark font-weight-medium mb-0 mr-3">Sizes:</p>
                <form>
                    <div className="custom-control custom-radio custom-control-inline">
                        <input type="radio" className="custom-control-input" id="size-1" name="size" />
                        <label className="custom-control-label" htmlFor="size-1">XS</label>
                    </div>
                    <div className="custom-control custom-radio custom-control-inline">
                        <input type="radio" className="custom-control-input" id="size-2" name="size" />
                        <label className="custom-control-label" htmlFor="size-2">S</label>
                    </div>
                    <div className="custom-control custom-radio custom-control-inline">
                        <input type="radio" className="custom-control-input" id="size-3" name="size" />
                        <label className="custom-control-label" htmlFor="size-3">M</label>
                    </div>
                    <div className="custom-control custom-radio custom-control-inline">
                        <input type="radio" className="custom-control-input" id="size-4" name="size" />
                        <label className="custom-control-label" htmlFor="size-4">L</label>
                    </div>
                    <div className="custom-control custom-radio custom-control-inline">
                        <input type="radio" className="custom-control-input" id="size-5" name="size" />
                        <label className="custom-control-label" htmlFor="size-5">XL</label>
                    </div>
                </form>
            </div>
            <div className="d-flex align-items-center mb-4 pt-2">
                <div className="input-group quantity mr-3" style={{ width: '130px' }}>
                    <div className="input-group-btn">
                        <button className="btn btn-primary btn-minus">
                            <i className="fa fa-minus" />
                        </button>
                    </div>
                    <input type="text" className="form-control bg-secondary text-center" defaultValue={1} />
                    <div className="input-group-btn">
                        <button className="btn btn-primary btn-plus">
                            <i className="fa fa-plus" />
                        </button>
                    </div>
                </div>
                <button className="btn btn-primary px-3"><i className="fa fa-shopping-cart mr-1" /> Add To Cart</button>
            </div>
            <div className="d-flex pt-2">
                <p className="text-dark font-weight-medium mb-0 mr-2">Share on:</p>
                <div className="d-inline-flex">
                    <a className="text-dark px-2" href>
                        <i className="fab fa-facebook-f" />
                    </a>
                    <a className="text-dark px-2" href>
                        <i className="fab fa-twitter" />
                    </a>
                    <a className="text-dark px-2" href>
                        <i className="fab fa-linkedin-in" />
                    </a>
                    <a className="text-dark px-2" href>
                        <i className="fab fa-pinterest" />
                    </a>
                </div>
            </div>
        </div>
    );
}

export default Option
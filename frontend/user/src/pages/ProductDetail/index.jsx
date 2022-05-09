import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom'
import productApi from '../../api/productApi'
import reviewApi from '../../api/reviewApi'
import Header from '../../components/Layout/Header'
import Description from '../../components/ProductDetail/Detail/Description'
import ImageProduct from '../../components/ProductDetail/Detail/Image'
import Option from '../../components/ProductDetail/Detail/Option'
import Recommend from '../../components/ProductDetail/Recommend'

const titleHeader = {
    mainTitle: 'SHOP DETAIL',
    extraTitle: 'Shop Detail'
}


const ProductDetail = () => {
    const { id, pageReview } = useParams();
    const [product, setProduct] = useState();
    const [reviews, setReviews] = useState();


    useEffect(() => {

        (async () => {

            const productResponse = await productApi.getProductById(id);
            const reviewResponse = await reviewApi.getReviews(id, pageReview);

            setProduct(productResponse.data);
            setReviews(reviewResponse.data);
        })()
    }, []);

    return (
        <>
            <Header titleHeader={titleHeader} />
            <div className="container-fluid py-5">
                <div className="row px-xl-5">
                    <ImageProduct image={product?.mainImage} />
                    <Option product={product} />
                </div>
                <Description reviewPagination={reviews} product={product} />
            </div>
            <Recommend />
        </>
    )
}

export default ProductDetail
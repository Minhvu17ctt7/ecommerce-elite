import React, { useEffect, useState } from 'react'
import { useParams, useSearchParams } from 'react-router-dom'
import categoryApi from '../../api/categoryApi'
import productApi from '../../api/productApi'
import Header from '../../components/Layout/Header'
import Product from '../../components/Shop/Product'
import Sidebar from '../../components/Shop/Sidebar'

const titleHeader = {
    mainTitle: 'OUR SHOP',
    extraTitle: 'SHOP'
}

const Shop = () => {
    const [products, setProducts] = useState();
    const [categories, setCategories] = useState([]);
    const [searchParams] = useSearchParams();
    const categoryId = searchParams.get("categoryId");
    const { page } = useParams();

    useEffect(() => {
        (async () => {
            const data = {
                "page": page,
                "sortField": "name",
                "sortName": "asc",
                "pageSize": 4,
                "search": categoryId ? `category:${categoryId},` : ""
            }

            const categoriesResponse = await categoryApi.getAllCategory();
            const productsResponse = await productApi.getAllProductFilter(data);
            setProducts(productsResponse.data);
            setCategories(categoriesResponse.data);

        })()
    }, []);
    return (
        <>
            <Header main titleHeader={titleHeader} />
            <div className="container-fluid pt-5">
                <div className="row px-xl-5">
                    <Sidebar categories={categories} />
                    <Product productPagination={products} />
                </div>
            </div>
        </>

    )
}

export default Shop
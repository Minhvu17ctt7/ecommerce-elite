import React from 'react'
import { useNavigate, useSearchParams } from 'react-router-dom';

const Sidebar = ({ categories }) => {
    const [searchParams] = useSearchParams();
    const navigation = useNavigate();
    const categoryId = searchParams.get("categoryId");
    const price = searchParams.get("price");
    const handleFilterCategory = (id) => {
        if (id) {
            navigation(`/shop?categoryId=${id}`);
        } else {
            navigation("/shop")
        }
    }


    return (

        <div className="col-lg-3 col-md-12">
            {/* Color Start */}
            <div className="border-bottom mb-4 pb-4">
                <h5 className="font-weight-semi-bold mb-4">Filter by color</h5>
                <form>
                    <div className="custom-control custom-checkbox d-flex align-items-center justify-content-between mb-3">
                        <input type="radio" className="custom-control-input" id="color-0" onChange={() => handleFilterCategory()} checked={categoryId == null} name="filter-categories" />
                        <label className="custom-control-label" htmlFor="color-0">All category</label>
                    </div>
                    {
                        categories.map((category, index) => {

                            return (
                                <div key={category.id} className="custom-control custom-checkbox d-flex align-items-center justify-content-between mb-3">
                                    <input type="radio" className="custom-control-input" id={`color-${index + 1}`} onChange={() => handleFilterCategory(category.id)} checked={categoryId != null && categoryId == category.id} name="filter-categories" />
                                    <label className="custom-control-label" htmlFor={`color-${index + 1}`}>{category.name}</label>
                                </div>)
                        })
                    }
                </form>
            </div>
            {/* Color End */}
            
            {/* Price Start */}
            <div className="border-bottom mb-4 pb-4">
                <h5 className="font-weight-semi-bold mb-4">Filter by price</h5>
                <form>
                    <div className="custom-control custom-checkbox d-flex align-items-center justify-content-between mb-3">
                        <input type="checkbox" className="custom-control-input" defaultChecked id="price-all" />
                        <label className="custom-control-label" htmlFor="price-all">All Price</label>
                    </div>
                    <div className="custom-control custom-checkbox d-flex align-items-center justify-content-between mb-3">
                        <input type="checkbox" className="custom-control-input" id="price-1" />
                        <label className="custom-control-label" htmlFor="price-1">0 - 100.000 VND</label>
                    </div>
                    <div className="custom-control custom-checkbox d-flex align-items-center justify-content-between mb-3">
                        <input type="checkbox" className="custom-control-input" id="price-2" />
                        <label className="custom-control-label" htmlFor="price-2">100.000 - 200.000 VND</label>
                    </div>
                    <div className="custom-control custom-checkbox d-flex align-items-center justify-content-between mb-3">
                        <input type="checkbox" className="custom-control-input" id="price-3" />
                        <label className="custom-control-label" htmlFor="price-3">200.000 - 400.000</label>
                    </div>
                    <div className="custom-control custom-checkbox d-flex align-items-center justify-content-between mb-3">
                        <input type="checkbox" className="custom-control-input" id="price-4" />
                        <label className="custom-control-label" htmlFor="price-4">400.000 - 500.000 VND</label>
                    </div>
                </form>
            </div>
            {/* Price End */}
            {/* Size Start */}
            {/* <div className="mb-5">
                <h5 className="font-weight-semi-bold mb-4">Filter by size</h5>
                <form>
                    <div className="custom-control custom-checkbox d-flex align-items-center justify-content-between mb-3">
                        <input type="checkbox" className="custom-control-input" defaultChecked id="size-all" />
                        <label className="custom-control-label" htmlFor="size-all">All Size</label>
                    </div>
                    <div className="custom-control custom-checkbox d-flex align-items-center justify-content-between mb-3">
                        <input type="checkbox" className="custom-control-input" id="size-1" />
                        <label className="custom-control-label" htmlFor="size-1">XS</label>
                    </div>
                    <div className="custom-control custom-checkbox d-flex align-items-center justify-content-between mb-3">
                        <input type="checkbox" className="custom-control-input" id="size-2" />
                        <label className="custom-control-label" htmlFor="size-2">S</label>
                    </div>
                    <div className="custom-control custom-checkbox d-flex align-items-center justify-content-between mb-3">
                        <input type="checkbox" className="custom-control-input" id="size-3" />
                        <label className="custom-control-label" htmlFor="size-3">M</label>
                    </div>
                    <div className="custom-control custom-checkbox d-flex align-items-center justify-content-between mb-3">
                        <input type="checkbox" className="custom-control-input" id="size-4" />
                        <label className="custom-control-label" htmlFor="size-4">L</label>
                    </div>
                    <div className="custom-control custom-checkbox d-flex align-items-center justify-content-between">
                        <input type="checkbox" className="custom-control-input" id="size-5" />
                        <label className="custom-control-label" htmlFor="size-5">XL</label>
                    </div>
                </form>
            </div> */}
            {/* Size End */}
        </div>
    );
}

export default Sidebar
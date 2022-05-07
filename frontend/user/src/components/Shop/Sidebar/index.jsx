import React from 'react'

const Sidebar = ({ categories }) => {
    return (

        <div className="col-lg-3 col-md-12">
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
                        <label className="custom-control-label" htmlFor="price-1">$0 - $100</label>
                    </div>
                    <div className="custom-control custom-checkbox d-flex align-items-center justify-content-between mb-3">
                        <input type="checkbox" className="custom-control-input" id="price-2" />
                        <label className="custom-control-label" htmlFor="price-2">$100 - $200</label>
                    </div>
                    <div className="custom-control custom-checkbox d-flex align-items-center justify-content-between mb-3">
                        <input type="checkbox" className="custom-control-input" id="price-3" />
                        <label className="custom-control-label" htmlFor="price-3">$200 - $300</label>
                    </div>
                    <div className="custom-control custom-checkbox d-flex align-items-center justify-content-between mb-3">
                        <input type="checkbox" className="custom-control-input" id="price-4" />
                        <label className="custom-control-label" htmlFor="price-4">$300 - $400</label>
                    </div>
                    <div className="custom-control custom-checkbox d-flex align-items-center justify-content-between">
                        <input type="checkbox" className="custom-control-input" id="price-5" />
                        <label className="custom-control-label" htmlFor="price-5">$400 - $500</label>
                    </div>
                </form>
            </div>
            {/* Price End */}
            {/* Color Start */}
            <div className="border-bottom mb-4 pb-4">
                <h5 className="font-weight-semi-bold mb-4">Filter by color</h5>
                <form>
                    {
                        categories.map((category, index) => (
                            <div className="custom-control custom-checkbox d-flex align-items-center justify-content-between mb-3">
                                <input type="checkbox" className="custom-control-input" id={`color-${index}`} name="filter-categories" />
                                <label className="custom-control-label" htmlFor={`color-${index}`}>{category.name}</label>
                            </div>))
                    }
                </form>
            </div>
            {/* Color End */}
            {/* Size Start */}
            <div className="mb-5">
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
            </div>
            {/* Size End */}
        </div>
    );
}

export default Sidebar
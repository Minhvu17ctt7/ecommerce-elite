import React from 'react';
import { Link } from 'react-router-dom';
import { usePagination } from './usePagination';

const range = (start, end) => {
    let length = end - start + 1;
    /*
        Create an array of certain length and set the elements within it from
      start value to end value.
    */
    return Array.from({ length }, (_, idx) => idx + start);
};

const Pagination = props => {
    const {
        url,
        totalPage,
        currentPage,
    } = props;

    const leftSiblingIndex = Math.max(currentPage - 1, 1);
    const rightSiblingIndex = Math.min(
        currentPage + 1,
        totalPage
    );

    const pageRange = range(leftSiblingIndex, rightSiblingIndex);
    console.log("pageRange", pageRange, leftSiblingIndex, rightSiblingIndex);

    return (
        <ul className="pagination justify-content-center mb-3">
            <li className={currentPage == 1 ? "page-item disabled" : "page-item"}>
                <Link to={`${url}${currentPage - 1}`} className="page-link" aria-label="Previous">
                    <span aria-hidden="true">«</span>
                    <span className="sr-only">Previous</span>
                </Link>
            </li>

            {
                pageRange.map((pageNumber, index) => {
                    return (
                        <li className={pageNumber == currentPage ? "page-item active" : "page-item"}>
                            <Link to={`${url}${index + 1}`} className="page-link"> {pageNumber} </Link>
                        </li>
                    );
                })}

            <li className={currentPage < totalPage.length ? "page-item disabled" : "page-item"} >
                <Link to={`${url}${currentPage + 1}`} className="page-link" aria-label="Next"> <span aria-hidden="true">»</span>
                    <span className="sr-only">Next</span>
                </Link>
            </li>
        </ul>
    );
};

export default Pagination;
import moment from 'moment';

export const convertTime = (time) => {
	return moment(time).format('DD/MM/YYYY LT');
};

export const formatCurrency = (value) => {
	return value.toLocaleString('en-US', { style: 'currency', currency: 'USD' });
}
#pragma once


// CRadioButtonEx

class CRadioButtonEx : public CButton
{
	DECLARE_DYNAMIC(CRadioButtonEx)

public:
	CRadioButtonEx();
	virtual ~CRadioButtonEx();
	virtual void DrawItem(LPDRAWITEMSTRUCT lpDrawItemStruct);
protected:
	DECLARE_MESSAGE_MAP()
};



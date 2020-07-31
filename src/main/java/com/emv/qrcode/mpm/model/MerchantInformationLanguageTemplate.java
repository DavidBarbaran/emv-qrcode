package com.emv.qrcode.mpm.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.emv.qrcode.decoder.Decoder;
import com.emv.qrcode.mpm.constants.EMVQRFieldCodes;
import com.emv.qrcode.mpm.constants.MerchantInformationFieldCodes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MerchantInformationLanguageTemplate implements Serializable {

  private static final long serialVersionUID = 6163271793010568887L;

  // Language Preference
  private TagLengthString languagePreference;

  // Merchant Name
  private TagLengthString merchantName;

  // Merchant City
  private TagLengthString merchantCity;

  // RFU for EMVCo
  private final List<TagLengthString> rFUforEMVCo = new LinkedList<>();

  public void setLanguagePreference(final String languagePreference) {
    this.languagePreference = new TagLengthString(MerchantInformationFieldCodes.MERCHANT_INFORMATION_ID_LANGUAGE_PREFERENCE, languagePreference);
  }

  public void setMerchantName(final String merchantName) {
    this.merchantName = new TagLengthString(MerchantInformationFieldCodes.MERCHANT_INFORMATION_ID_MERCHANT_NAME, merchantName);
  }

  public void setMerchantCity(final String merchantCity) {
    this.merchantCity = new TagLengthString(MerchantInformationFieldCodes.MERCHANT_INFORMATION_ID_MERCHANT_CITY, merchantCity);
  }

  public void addRFUforEMVCO(final String value) {
    rFUforEMVCo.add(new TagLengthString(value.substring(0, Decoder.ID_WORD_COUNT), value.substring(Decoder.ID_WORD_COUNT)));
  }

  @Override
  public String toString() {

    final StringBuilder sb = new StringBuilder();

    Optional.ofNullable(languagePreference).ifPresent(tlv -> sb.append(tlv.toString()));
    Optional.ofNullable(merchantName).ifPresent(tlv -> sb.append(tlv.toString()));
    Optional.ofNullable(merchantCity).ifPresent(tlv -> sb.append(tlv.toString()));

    for (final TagLengthString tagLengthString : rFUforEMVCo) {
      Optional.ofNullable(tagLengthString).ifPresent(tlv -> sb.append(tlv.toString()));
    }

    final String string = sb.toString();

    if (StringUtils.isBlank(string)) {
      return StringUtils.EMPTY;
    }

    return String.format("%s%02d%s", EMVQRFieldCodes.ID_MERCHANT_INFORMATION_LANGUAGE_TEMPLATE, string.length(), string);
  }

}
